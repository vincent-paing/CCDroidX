package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.databinding.ItemProjectBinding
import dev.aungkyawpaing.ccdroidx.utils.extensions.diffCallBackWith
import dev.aungkyawpaing.ccdroidx.utils.extensions.inflater
import dev.aungkyawpaing.ccdroidx.utils.extensions.withSafeAdapterPosition

class ProjectListAdapter(
  private val onOpenRepoClick: ((project: Project) -> Unit),
  private val onDeleteClick: ((project: Project) -> Unit),
  private val onToggleMute: ((project: Project) -> Unit)
) : ListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(
  diffCallBackWith(
    areItemTheSame = { item1, item2 -> item1.name == item2.name && item1.webUrl == item2.webUrl },
    areContentsTheSame = { item1, item2 -> item1 == item2 }
  )
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
    return ProjectViewHolder(
      ItemProjectBinding.inflate(parent.inflater(), parent, false),
      onClickMenu = { view, position ->
        showMenu(view, getItem(position))
      },
    )
  }

  override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  private fun showMenu(view: View, project: Project) {
    val popup = PopupMenu(view.context, view)
    popup.menuInflater.inflate(R.menu.menu_item_project, popup.menu)

    popup.menu.findItem(R.id.action_toggle_mute).title = if (project.isMuted) {
      view.context.getString(R.string.action_item_project_unmute)
    } else {
      view.context.getString(R.string.action_item_project_mute)
    }

    popup.setOnMenuItemClickListener { menuItem: MenuItem ->
      when (menuItem.itemId) {
        R.id.action_open_repo -> {
          onOpenRepoClick(project)
          return@setOnMenuItemClickListener true
        }
        R.id.action_delete -> {
          onDeleteClick(project)
          return@setOnMenuItemClickListener true
        }
        R.id.action_toggle_mute -> {
          onToggleMute(project)
          return@setOnMenuItemClickListener true
        }
        else -> {
          return@setOnMenuItemClickListener false
        }
      }
    }
    popup.show()
  }

  class ProjectViewHolder(
    private val binding: ItemProjectBinding,
    private val onClickMenu: ((view: View, position: Int) -> Unit),
  ) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.ivMenu.setOnClickListener { view ->
        withSafeAdapterPosition { position ->
          onClickMenu(view, position)
        }
      }
    }

    fun bind(item: Project) {
      binding.item = item
      binding.executePendingBindings()
    }
  }
}