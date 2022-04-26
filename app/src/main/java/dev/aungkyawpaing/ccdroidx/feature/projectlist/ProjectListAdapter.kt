package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.graphics.drawable.GradientDrawable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.databinding.ItemProjectBinding
import dev.aungkyawpaing.ccdroidx.utils.extensions.diffCallBackWith
import dev.aungkyawpaing.ccdroidx.utils.extensions.inflater
import dev.aungkyawpaing.ccdroidx.utils.extensions.withSafeAdapterPosition
import org.ocpsoft.prettytime.PrettyTime


class ProjectListAdapter(
  private val onOpenRepoClick: ((project: Project) -> Unit),
  private val onDeleteClick: ((project: Project) -> Unit)
) :
  ListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(
    diffCallBackWith(
      areItemTheSame = { item1, item2 -> item1.name == item2.name && item1.webUrl == item2.webUrl },
      areContentsTheSame = { item1, item2 -> item1 == item2 }
    )
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
    return ProjectViewHolder(
      ItemProjectBinding.inflate(parent.inflater(), parent, false),
      onOpenRepoClick = { position ->
        onOpenRepoClick(getItem(position))
      },
      onDeleteClick = { position ->
        onDeleteClick(getItem(position))
      }
    )
  }

  override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  class ProjectViewHolder(
    private val binding: ItemProjectBinding,
    private val onOpenRepoClick: ((position: Int) -> Unit),
    private val onDeleteClick: ((position: Int) -> Unit)
  ) : RecyclerView.ViewHolder(binding.root) {

    private val prettyTime = PrettyTime()

    init {
      binding.ivMenu.setOnClickListener { view ->
        withSafeAdapterPosition { position ->
          showMenu(view, position)
        }
      }
    }

    fun bind(item: Project) {
      val buildStatusColor = when (item.activity) {
        BuildState.SLEEPING -> {
          when (item.lastBuildStatus) {
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
      shape.setColor(ContextCompat.getColor(itemView.context, buildStatusColor))
      shape.setStroke(
        1,
        MaterialColors.getColor(itemView, com.google.android.material.R.attr.colorOnSurface)
      )

      binding.apply {
        tvProjectName.text = item.name
        viewBuildStatus.background = shape
        tvLastSyncTime.text = prettyTime.format(item.lastBuildTime)
      }
    }

    private fun showMenu(view: View, position: Int) {
      val popup = PopupMenu(itemView.context, view)
      popup.menuInflater.inflate(R.menu.menu_item_project, popup.menu)

      popup.setOnMenuItemClickListener { menuItem: MenuItem ->
        when (menuItem.itemId) {
          R.id.action_open_repo -> {
            onOpenRepoClick(position)
            return@setOnMenuItemClickListener true
          }
          R.id.action_delete -> {
            onDeleteClick(position)
            return@setOnMenuItemClickListener true
          }
          else -> {
            return@setOnMenuItemClickListener false
          }
        }


      }
      popup.show()
    }

  }
}