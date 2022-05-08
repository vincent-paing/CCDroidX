package dev.aungkyawpaing.ccdroidx.utils.databinding

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * A ViewModel that is also an Observable,
 * to be used with the Data Binding Library.
 */
open class ObservableViewModel : ViewModel(), Observable {
  private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

  override fun addOnPropertyChangedCallback(
    callback: Observable.OnPropertyChangedCallback
  ) {
    callbacks.add(callback)
  }

  override fun removeOnPropertyChangedCallback(
    callback: Observable.OnPropertyChangedCallback
  ) {
    callbacks.remove(callback)
  }

  /**
   * Notifies observers that all properties of this instance have changed.
   */
  fun notifyChange() {
    callbacks.notifyCallbacks(this, 0, null)
  }

  /**
   * Notifies observers that a specific property has changed. The getter for the
   * property that changes should be marked with the @Bindable annotation to
   * generate a field in the BR class to be used as the fieldId parameter.
   *
   * @param fieldId The generated BR id for the Bindable field.
   */
  fun notifyPropertyChanged(fieldId: Int) {
    callbacks.notifyCallbacks(this, fieldId, null)
  }
}
