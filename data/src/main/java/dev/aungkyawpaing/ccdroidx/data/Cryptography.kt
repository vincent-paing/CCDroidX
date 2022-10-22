package dev.aungkyawpaing.ccdroidx.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.inject.Singleton


/**
 * Manage cryptographic key in keystore
 * requires previous user authentication to have been performed
 * https://gist.github.com/msramalho/a95b1ea880fa3f3d6f70099ccf72ff62
 */
@Singleton
class Cryptography constructor(
  private val keyName: String
) {

  companion object {
    private const val TRANSFORMATION =
      "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
    private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    private const val SEPARATOR = ","
  }

  private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
    load(null)
  }
  private val secretKey: SecretKey = getKey() ?: generateKey()

  private fun getKey(): SecretKey? {
    return (keyStore.getEntry(keyName, null) as? KeyStore.SecretKeyEntry)?.secretKey
  }

  private fun generateKey(): SecretKey {
    val keyGenerator: KeyGenerator =
      KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
      keyName,
      KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
      .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
      .build()
    keyGenerator.init(keyGenParameterSpec)
    return keyGenerator.generateKey()
  }

  @Throws(
    NoSuchPaddingException::class,
    NoSuchAlgorithmException::class,
    InvalidKeyException::class,
    BadPaddingException::class,
    IllegalBlockSizeException::class
  )
  fun encrypt(toEncrypt: String): String {
    val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val iv: String = Base64.encodeToString(cipher.iv, Base64.NO_WRAP)
    val encrypted: String = Base64.encodeToString(
      cipher.doFinal(toEncrypt.toByteArray(StandardCharsets.UTF_8)),
      Base64.NO_WRAP
    )
    return encrypted + SEPARATOR + iv
  }

  @Throws(
    NoSuchPaddingException::class,
    NoSuchAlgorithmException::class,
    InvalidAlgorithmParameterException::class,
    InvalidKeyException::class,
    BadPaddingException::class,
    IllegalBlockSizeException::class
  )
  fun decrypt(toDecrypt: String): String {
    val parts = toDecrypt.split(SEPARATOR).toTypedArray()
    if (parts.size != 2) throw AssertionError("String to decrypt must be of the form: 'BASE64_DATA" + SEPARATOR + "BASE64_IV'")
    val encrypted: ByteArray = Base64.decode(parts[0], Base64.NO_WRAP)
    val iv: ByteArray = Base64.decode(parts[1], Base64.NO_WRAP)
    val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
    val spec = IvParameterSpec(iv)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
    return String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
  }
}