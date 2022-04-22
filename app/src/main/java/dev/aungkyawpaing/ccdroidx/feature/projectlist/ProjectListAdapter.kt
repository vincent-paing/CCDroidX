package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.elevation.SurfaceColors
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.databinding.ItemProjectBinding
import dev.aungkyawpaing.ccdroidx.utils.extensions.diffCallBackWith
import dev.aungkyawpaing.ccdroidx.utils.extensions.inflater


class ProjectListAdapter :
  ListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(
    diffCallBackWith(
      areItemTheSame = { item1, item2 -> item1.name == item2.name && item1.webUrl == item2.webUrl },
      areContentsTheSame = { item1, item2 -> item1 == item2 }
    )
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
    return ProjectViewHolder(
      ItemProjectBinding.inflate(parent.inflater(), parent, false)
    )
  }

  override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ProjectViewHolder(
    private val binding: ItemProjectBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Project) {

      val buildStatusColor = when (item.activity) {
        BuildState.SLEEPING -> {
          when (item.lastBuildStatus) {
            BuildStatus.SUCCESS -> R.color.build_success
            BuildStatus.FAILURE -> R.color.build_fail
            BuildStatus.EXCEPTION -> R.color.build_fail
            BuildStatus.UNKNOWN -> TODO()
          }
        }
        BuildState.BUILDING, BuildState.CHECKING_MODIFICATIONS -> {
          R.color.build_in_progress
        }
      }

      val shape = GradientDrawable()
      shape.shape = GradientDrawable.OVAL
      shape.setColor(ContextCompat.getColor(itemView.context, buildStatusColor))
      shape.setStroke(
        1,
        MaterialColors.getColor(itemView, com.google.android.material.R.attr.colorOnSurface)
      )

      binding.apply {
        tvProjectName.text = item.name
        viewBuildStatus.background = shape
      }
    }

  }
}