package com.sugarspoon.eventcheckin.ui.details

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.eventcheckin.ui.base.BaseViewModel
import com.sugarspoon.eventcheckin.utils.onCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: EventRepository
) : BaseViewModel<DetailsIntent, DetailsState>() {

    override fun handle(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.SetCheckin -> setCheckin(intent.customer)
        }
    }

    private fun setCheckin(customer: CustomerEntity) = repository.checkin(customer).onCollect(
        onLoading = {
            _state.value = DetailsState.DisplayLoading(isLoading = it)
        },
        onSuccess = {
            _state.value = DetailsState.DisplaySuccess
        },
        onError = {
            _state.value = DetailsState.DisplayError(error = it.message.orEmpty())
        }
    )
}