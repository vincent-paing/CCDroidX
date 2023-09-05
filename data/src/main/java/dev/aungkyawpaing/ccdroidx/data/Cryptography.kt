package dev.aungkyawpaing.ccdroidx.data

interface Cryptography {

  fun encrypt(toEncrypt: String): String

  fun decrypt(toDecrypt: String): String

}