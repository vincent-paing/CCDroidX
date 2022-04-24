package dev.aungkyawpaing.ccdroidx.feature.sync

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import dev.aungkyawpaing.ccdroidx.SyncedStateProto
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

object SyncedStateProtoSerializer : Serializer<SyncedStateProto?> {

  override val defaultValue: SyncedStateProto? get() = null

  override suspend fun readFrom(input: InputStream): SyncedStateProto? {
    return try {
      SyncedStateProto.ADAPTER.decode(input)
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto", exception)
    }
  }

  override suspend fun writeTo(t: SyncedStateProto?, output: OutputStream) {
    t?.encode(output)
  }

}