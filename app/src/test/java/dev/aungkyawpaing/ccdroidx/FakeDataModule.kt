package dev.aungkyawpaing.ccdroidx

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import dev.aungkyawpaing.ccdroidx.data.Cryptography
import dev.aungkyawpaing.ccdroidx.data.DataModule
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [DataModule::class]
)
class FakeDataModule {

  @Provides
  @Singleton
  fun provideFakeCryptography(): Cryptography {
    return object : Cryptography {
      override fun encrypt(toEncrypt: String): String = toEncrypt

      override fun decrypt(toDecrypt: String): String = toDecrypt

    }
  }

}