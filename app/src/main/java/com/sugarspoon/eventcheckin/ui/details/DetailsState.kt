package com.sugarspoon.eventcheckin.ui.details

import com.sugarspoon.data.model.entity.EventEntity

sealed class DetailsState {
    data class LoadEventDetails(val eventDetail: EventEntity) : DetailsState()
    data class DisplayLoading(val isLoading: Boolean) : DetailsState()
    data class DisplayError(val error: String) : DetailsState()
    object DismissDialog: DetailsState()
}