package dev.aungkyawpaing.ccdroidx.feature.projectlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.databinding.FragmentProjectListBinding
import dev.aungkyawpaing.ccdroidx.feature.add.AddProjectViewModel

@AndroidEntryPoint
class ProjectListFragment : Fragment() {

  private var _binding: FragmentProjectListBinding? = null
  private val binding get() = _binding!!

  private val projectListAdapter = ProjectListAdapter()
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
//    (requireActivity() as? AppCompatActivity)?.setSupportActionBar(binding.toolBar)
    binding.fabAdd.setOnClickListener {
      findNavController().navigate(
        ProjectListFragmentDirections.actionFragmentProjectListToAddProjectDialog()
      )
    }
    binding.rvProjects.apply {
      adapter = projectListAdapter
      layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }
    viewModel.projectListLiveData.observe(viewLifecycleOwner) { projectList ->
      projectListAdapter.submitList(projectList)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }


}