package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.content.Context
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import org.ocpsoft.prettytime.PrettyTime

object Converter {

  private val prettyTime = PrettyTime()

  @JvmStatic
  fun fromLastSyncedStatus(context: Context, lastSyncedStatus: LastSyncedStatus?): String {
    if (lastSyncedStatus == null) {
      return "Welcome!"
    } else {
      when (lastSyncedStatus.lastSyncedState) {
        LastSyncedState.SYNCING -> {
          return context.getString(
            R.string.syncing
          )
        }
        LastSyncedState.SUCCESS, LastSyncedState.FAILED -> {
          return context.getString(
            R.string.last_synced_x, prettyTime.format(lastSyncedStatus.lastSyncedDateTime)
          )
        }
      }
    }

  }
}