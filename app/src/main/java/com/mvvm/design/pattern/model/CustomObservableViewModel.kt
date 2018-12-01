package com.mvvm.design.pattern.model

import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry

abstract class CustomObservableViewModel : ViewModel(), Observable {
    private val callback = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callbackRemove: Observable.OnPropertyChangedCallback?) {
        callback.remove(callbackRemove)
    }

    override fun addOnPropertyChangedCallback(callbackAdd: Observable.OnPropertyChangedCallback?) {
        callback.add(callbackAdd)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callback.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callback.notifyCallbacks(this, fieldId, null)
    }

}