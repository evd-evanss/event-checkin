package com.sugarspoon.eventcheckin.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sugarspoon.eventcheckin.utils.SingleLiveEvent

abstract class BaseViewModel<Intent, State> : ViewModel() {

    protected val _state = SingleLiveEvent<State>()
    val state: LiveData<State> = _state

    abstract fun handle(intent: Intent)

}