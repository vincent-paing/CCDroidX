package dev.aungkyawpaing.ccdroidx.feature.notification.prompt.permssionflow

import android.Manifest
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationPermissionFlowImpl @Inject constructor(
  @ApplicationContext val context: Context
) : NotificationPermissionFlow {

  private val flow = PermissionFlow.getInstance()

  override fun getFlow(): Flow<Boolean> {
    return flow.getPermissionState(Manifest.permission.POST_NOTIFICATIONS).map { state ->
      state.isGranted
    }
  }

}