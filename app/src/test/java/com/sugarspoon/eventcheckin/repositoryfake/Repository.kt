package com.sugarspoon.eventcheckin.repositoryfake

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.data.repositories.EventRepository
import kotlinx.coroutines.flow.flow

class Repository() : EventRepository {

    override fun getEvents() = flow {
        emit(listOf<EventEntity>())
    }

    override fun getEventDetail(id: String) = flow {
        emit(getFakeEventResponse())
    }

    override fun checkin(customer: CustomerEntity) = flow {
        emit(Any())
    }

    private fun getFakeEventResponse() = EventEntity(
        date = 1645464356,
        title = "",
        description = "Feira de adoção de animais",
        image = "www.google.com.br/logo.png",
        latitude = -23.6073315,
        longitude = -46.5252506,
        price = 18.90,
        id = "1"
    )
}