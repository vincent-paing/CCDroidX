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
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.databinding.FragmentProjectListBinding

@AndroidEntryPoint
class ProjectListFragment : Fragment() {

  private var _binding: FragmentProjectListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: ProjectListViewModel by viewModels()

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

    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        // In Compose world
        Mdc3Theme {
          ProjectListPage(viewModel, onClickSettings = {
            findNavController().navigate(
              ProjectListFragmentDirections.actionFragmentProjectListToSettingsFragment()
            )
          })
        }
      }
    }

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}