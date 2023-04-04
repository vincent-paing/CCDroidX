package dev.aungkyawpaing.ccdroidx.feature.notification.prompt.permssionflow

import kotlinx.coroutines.flow.Flow

interface NotificationPermissionFlow {

  fun getFlow(): Flow<Boolean>

}