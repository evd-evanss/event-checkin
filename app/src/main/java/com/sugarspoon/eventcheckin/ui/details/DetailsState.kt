package com.sugarspoon.eventcheckin.ui.details

sealed class DetailsState {
    data class DisplayLoading(val isLoading: Boolean) : DetailsState()
    data class DisplayError(val error: String) : DetailsState()
    data class ShareEvent(val content: String) : DetailsState()
    object DisplaySuccess: DetailsState()
}