package com.sugarspoon.eventcheckin.ui.events

import com.sugarspoon.data.model.entity.EventEntity

sealed class EventsState {
    data class UpdateData(val data: List<EventEntity>) : EventsState()
    data class DisplayError(val isLoadEventsError: Boolean) : EventsState()
    data class DisplayShimmer(val isLoading: Boolean) : EventsState()
    data class DisplayLoading(val isLoading: Boolean) : EventsState()
    data class OpenDetail(val eventDetail: EventEntity) : EventsState()
}
