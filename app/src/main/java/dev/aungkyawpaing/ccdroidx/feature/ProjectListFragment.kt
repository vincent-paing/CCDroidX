package dev.aungkyawpaing.ccdroidx.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.databinding.FragmentProjectListBinding

@AndroidEntryPoint
class ProjectListFragment : Fragment() {

  private var _binding: FragmentProjectListBinding? = null
  private val binding get() = _binding!!

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
    binding.fabAdd.setOnClickListener {
      findNavController().navigate(
        ProjectListFragmentDirections.actionFragmentProjectListToAddProjectDialog()
      )
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }


}