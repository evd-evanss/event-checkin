package com.sugarspoon.data.repositories

import com.sugarspoon.data.model.Customer
import com.sugarspoon.data.sources.EventsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EventRepository {
    fun getEvents(): Flow<List<Any>>
    fun getEventDetail(id: Int): Flow<Any>
    fun setCheckin(customer: Customer): Flow<Any>
}

class EventRepositoryImpl @Inject constructor(
    private val dataSource: EventsDataSource
): EventRepository {

    override fun getEvents() = dataSource.getEvents()

    override fun getEventDetail(id: Int) = dataSource.getEventsDetail(id)

    override fun setCheckin(customer: Customer) = dataSource.setCheckin(customer)

}