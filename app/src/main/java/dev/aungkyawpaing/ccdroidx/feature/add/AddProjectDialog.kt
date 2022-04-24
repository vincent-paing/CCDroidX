package dev.aungkyawpaing.ccdroidx.feature.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.databinding.AddProjectDialogBinding
import dev.aungkyawpaing.ccdroidx.utils.extensions.hideKeyboard
import dev.aungkyawpaing.ccdroidx.utils.extensions.showShortToast

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
      it.hideKeyboard()
      viewModel.getProjectsFromFeed(binding.textFieldFeedUrl.text.toString())
    }

    binding.buttonCancel.setOnClickListener {
      this.dismiss()
    }

    viewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
      if (isLoading) {
        binding.progressIndicator.show()
      } else {
        binding.progressIndicator.hide()
      }
      binding.buttonNext.isEnabled = !isLoading
      binding.textFieldFeedUrl.isEnabled = !isLoading
      binding.buttonCancel.isEnabled = !isLoading
    }

    viewModel.projectListLiveEvent.observe(viewLifecycleOwner) { projectList ->
      MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.select_project)
        .setItems(
          projectList.map {
            it.name
          }.toTypedArray()
        ) { dialog, which ->
          viewModel.onSelectProject(projectList[which])
          dialog.dismiss()
        }
        .setNegativeButton(android.R.string.cancel) { dialog, _ ->
          dialog.dismiss()
        }
        .create()
        .show()
    }

    viewModel.dismissLiveEvent.observe(viewLifecycleOwner) {
      dismiss()
    }

    viewModel.errorLiveEvent.observe(viewLifecycleOwner) {
      requireContext().showShortToast(it)
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

}