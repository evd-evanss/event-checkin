package com.sugarspoon.eventcheckin.ui.events

sealed class EventsIntent {
    object LoadEvents : EventsIntent()
    data class GetDetailsById(val id: String) : EventsIntent()
}
