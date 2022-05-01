package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object SyncIntervalProtoSerializer : Serializer<SyncIntervalProto?> {

  override val defaultValue: SyncIntervalProto
    get() = SyncIntervalProto(
      15,
      SyncIntervalProto.TimeUnitProto.MINUTES
    )

  override suspend fun readFrom(input: InputStream): SyncIntervalProto? {
    return try {
      SyncIntervalProto.ADAPTER.decode(input)
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto", exception)
    }
  }

  override suspend fun writeTo(t: SyncIntervalProto?, output: OutputStream) {
    t?.encode(output)
  }

}