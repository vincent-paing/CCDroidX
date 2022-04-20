package dev.aungkyawpaing.ccdroidx.feature.add

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.databinding.AddProjectDialogBinding
import timber.log.Timber

@AndroidEntryPoint
class AddProjectDialog : DialogFragment() {

  private var _binding: AddProjectDialogBinding? = null
  private val binding get() = _binding!!
  private val viewModel: AddProjectViewModel by viewModels()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    _binding = AddProjectDialogBinding.inflate(this.layoutInflater)
    return MaterialAlertDialogBuilder(requireContext())
      .setTitle(R.string.add_new_project)
      .setView(binding.root)
      .create()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.buttonNext.setOnClickListener {
      viewModel.getProjectsFromFeed(binding.textFieldFeedUrl.text.toString())
    }

    binding.buttonCancel.setOnClickListener {
      this.dismiss()
    }

    viewModel.projectListLiveEvent.observe(viewLifecycleOwner) { projectList ->
      Timber.i(projectList.toString())
      MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.select_project)
        .setItems(
          projectList.map {
            it.name
          }.toTypedArray()
        ) { dialog, which ->
          Timber.i(projectList[which].toString())
          dialog.dismiss()
        }
        .setNegativeButton(android.R.string.cancel) { dialog, _ ->
          dialog.dismiss()
        }
        .create()
        .show()
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

}