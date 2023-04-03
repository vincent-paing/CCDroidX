package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface NotificationDismissStore {

  fun saveDismissTimeStamp(dateTime: LocalDateTime)

  fun getDismissTimeStamp(): Flow<LocalDateTime?>

}