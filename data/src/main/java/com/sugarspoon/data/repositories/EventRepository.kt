package com.sugarspoon.data.repositories

import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.data.model.entity.toEventEntity
import com.sugarspoon.data.model.request.toRequest
import com.sugarspoon.data.sources.EventsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface EventRepository {
    fun getEvents(): Flow<List<EventEntity>>
    fun getEventDetail(id: String): Flow<EventEntity>
    fun checkin(customer: CustomerEntity): Flow<Any>
}

class EventRepositoryImpl @Inject constructor(
    private val dataSource: EventsDataSource
): EventRepository {

    override fun getEvents() = dataSource.getEvents().map { response ->
        val entityList = mutableListOf<EventEntity>()
        response.forEach {
            entityList.add(it.toEventEntity())
        }
        entityList
    }

    override fun getEventDetail(id: String) = dataSource.getEventsDetail(id).map { response ->
        response.toEventEntity()
    }

    override fun checkin(customer: CustomerEntity) = dataSource.setCheckin(customer.toRequest()).map {
        it.toString()
    }

}