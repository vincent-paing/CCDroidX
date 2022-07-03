package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import dev.aungkyawpaing.ccdroidx.SyncedStateProto
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

val Context.lastSyncedStateDataStore: DataStore<SyncedStateProto?> by dataStore(
  fileName = "last_synced_state.pb",
  serializer = SyncedStateProtoSerializer
)

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