package dev.aungkyawpaing.ccdroidx.feature

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object DataBindingAdapters {

  @BindingAdapter("android:visibility")
  @JvmStatic
  fun setVisibility(view: View, value: Boolean) {
    view.isVisible = value
  }
}