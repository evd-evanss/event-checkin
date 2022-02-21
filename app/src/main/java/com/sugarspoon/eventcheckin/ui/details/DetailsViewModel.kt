package com.sugarspoon.eventcheckin.ui.details

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
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
            is DetailsIntent.LoadDetails -> loadDetails(intent.eventDetail)
            is DetailsIntent.SubscribeCustomer -> subscribeCustomer(intent.customer)
        }
    }

    private fun loadDetails(eventDetail: EventEntity) {
        _state.value = DetailsState.LoadEventDetails(eventDetail = eventDetail)
    }

    private fun subscribeCustomer(customer: CustomerEntity) = repository.subscribe(customer).onCollect(
        onLoading = {
            _state.value = DetailsState.DisplayLoading(isLoading = it)
        },
        onSuccess = {
            _state.value = DetailsState.DismissDialog
        },
        onError = {
            _state.value = DetailsState.DisplayError(error = it.message.orEmpty())
        }
    )
}