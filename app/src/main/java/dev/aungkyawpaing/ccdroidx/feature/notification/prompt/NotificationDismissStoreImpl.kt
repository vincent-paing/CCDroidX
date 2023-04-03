package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.wire.Instant
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class NotificationDismissStoreImpl @Inject constructor(
  @ApplicationContext private val context: Context,
) : NotificationDismissStore {

  private val Context.notificationPromptDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "notification_prompt"
  )
  private val DISMISS_TIMESTAMP_KEY = longPreferencesKey("notificationPromptDismissTimeStamp")


  override suspend fun saveDismissTimeStamp(dateTime: LocalDateTime) {
    context.notificationPromptDataStore.edit {
      it[DISMISS_TIMESTAMP_KEY] = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }
  }

  override fun getDismissTimeStamp(): Flow<LocalDateTime?> {
    return context.notificationPromptDataStore.data.map { pref ->
      val timeInMilli = pref[DISMISS_TIMESTAMP_KEY] ?: return@map null
      return@map LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMilli), ZoneOffset.UTC)
    }
  }


}