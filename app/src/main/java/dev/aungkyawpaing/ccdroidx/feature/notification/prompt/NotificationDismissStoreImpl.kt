package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import javax.inject.Inject

class NotificationDismissStoreImpl @Inject constructor() : NotificationDismissStore {
  override fun saveDismissTimeStamp(dateTime: LocalDateTime) {
    // TODO
  }

  override fun getDismissTimeStamp(): Flow<LocalDateTime> {
    // TODO
    return flow { LocalDateTime.now() }
  }


}