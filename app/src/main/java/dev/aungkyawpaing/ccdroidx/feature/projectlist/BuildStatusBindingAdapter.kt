package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.color.MaterialColors
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus

object BuildStatusBindingAdapter {

  @BindingAdapter("buildStatus", "buildState")
  @JvmStatic
  fun from(view: View, buildStatus: BuildStatus, buildState: BuildState) {
    val buildStatusColor = when (buildState) {
      BuildState.SLEEPING -> {
        when (buildStatus) {
          BuildStatus.SUCCESS -> R.color.build_success
          BuildStatus.FAILURE -> R.color.build_fail
          BuildStatus.EXCEPTION -> R.color.build_fail
          BuildStatus.UNKNOWN -> R.color.build_fail
        }
      }
      BuildState.BUILDING, BuildState.CHECKING_MODIFICATIONS -> {
        R.color.build_in_progress
      }
    }

    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.setColor(ContextCompat.getColor(view.context, buildStatusColor))
    shape.setStroke(
      1,
      MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnSurface)
    )

    view.background = shape
  }
}