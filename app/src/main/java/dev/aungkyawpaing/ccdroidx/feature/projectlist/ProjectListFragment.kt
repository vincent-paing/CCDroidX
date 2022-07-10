package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter3.Mdc3Theme
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.databinding.FragmentProjectListBinding
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import org.ocpsoft.prettytime.PrettyTime

@AndroidEntryPoint
class ProjectListFragment : Fragment() {

  private var _binding: FragmentProjectListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: ProjectListViewModel by viewModels()

  private val prettyTime = PrettyTime()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentProjectListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.viewModel = viewModel
    binding.lifecycleOwner = viewLifecycleOwner
    binding.fabAdd.setOnClickListener {
      findNavController().navigate(
        ProjectListFragmentDirections.actionFragmentProjectListToAddProjectDialog()
      )
    }

    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        // In Compose world
        Mdc3Theme {
          ProjectListPage(viewModel)
        }
      }
    }

    binding.toolBar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.action_sync -> {
          viewModel.onPressSync()
          return@setOnMenuItemClickListener true
        }
        R.id.action_settings -> {
          findNavController().navigate(
            ProjectListFragmentDirections.actionFragmentProjectListToSettingsFragment()
          )
          return@setOnMenuItemClickListener true
        }
        else -> {
          return@setOnMenuItemClickListener false
        }
      }

    }
    
    viewModel.lastSyncedLiveData.observe(viewLifecycleOwner) { lastSyncedStatus ->
      updateSubtitle(lastSyncedStatus)
    }
  }

  private fun updateSubtitle(lastSyncedStatus: LastSyncedStatus?) {
    if (lastSyncedStatus == null) {
      binding.toolBar.subtitle = "Welcome!"
    } else {
      when (lastSyncedStatus.lastSyncedState) {
        LastSyncedState.SYNCING -> {
          binding.toolBar.subtitle = getString(
            R.string.syncing
          )
        }
        LastSyncedState.SUCCESS, LastSyncedState.FAILED -> {
          binding.toolBar.subtitle = getString(
            R.string.last_synced_x, prettyTime.format(lastSyncedStatus.lastSyncedDateTime)
          )
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    updateSubtitle(viewModel.lastSyncedLiveData.value)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun onDeleteProject(project: Project) {
    MaterialAlertDialogBuilder(requireContext())
      .setTitle(getString(R.string.confirm_delete_title))
      .setMessage(getString(R.string.confirm_delete_message))
      .setPositiveButton(R.string.action_item_project_delete_project) { _, _ ->
        viewModel.onDeleteProject(project)
      }
      .setNegativeButton(android.R.string.cancel, null)
      .show()
  }

  private fun onToggleMute(project: Project) {
    viewModel.onToggleMute(project)
  }

}