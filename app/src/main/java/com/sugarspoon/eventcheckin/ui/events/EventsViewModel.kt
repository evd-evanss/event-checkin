package com.sugarspoon.eventcheckin.ui.events

import com.sugarspoon.data.repositories.EventRepository
import com.sugarspoon.eventcheckin.ui.base.BaseViewModel
import com.sugarspoon.eventcheckin.utils.onCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventRepository
) : BaseViewModel<EventsIntent, EventsState>() {

    override fun handle(intent: EventsIntent) {
        when (intent) {
            is EventsIntent.LoadEvents -> loadEvents()
            is EventsIntent.GetDetailsById -> getDetailsById(intent.id)
        }
    }

    private fun loadEvents() {
        repository.getEvents().onCollect(
            onLoading = {
                _state.value = EventsState.DisplayShimmer(isLoading = it)
            },
            onSuccess = {
                _state.value = EventsState.UpdateData(it)
            },
            onError = {
                _state.value = EventsState.DisplayError(error = it.message ?: DEFAULT_MESSAGE_ERROR)
            }
        )
    }

    private fun getDetailsById(id: String) = repository.getEventDetail(id).onCollect(
        onLoading = {
            _state.value = EventsState.DisplayLoading(isLoading = it)
        },
        onSuccess = { eventDetail ->
            _state.value = EventsState.OpenDetail(eventDetail = eventDetail)
        },
        onError = {
            EventsState.DisplayError(error = it.message ?: DEFAULT_MESSAGE_ERROR)
        }
    )

    companion object {
        private const val DEFAULT_MESSAGE_ERROR =
            "Todo mundo erra e dessa vez fui eu :(, tente novamente mais tarde"
    }
}