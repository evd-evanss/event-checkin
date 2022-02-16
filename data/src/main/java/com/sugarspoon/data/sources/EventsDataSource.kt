package com.sugarspoon.data.sources

import com.sugarspoon.data.RetrofitServiceFactory
import com.sugarspoon.data.model.Customer
import com.sugarspoon.data.model.Event
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

class EventsDataSource @Inject constructor(
    retrofit: Retrofit.Builder,
    okHttpClient: OkHttpClient
) {

    private val service = RetrofitServiceFactory(retrofit, okHttpClient)
        .newInstance<Service>()

    fun getEvents() = flow {
        emit(service.getEvents())
    }

    fun getEventsDetail(id: Int) = flow {
        emit(service.getEventsDetail(id))
    }

    fun setCheckin(customer: Customer) = flow {
        emit(service.setCheckin(customer))
    }

    interface Service {

        @GET("events")
        suspend fun getEvents(): List<Event>

        @GET("events")
        suspend fun getEventsDetail(@Body id: Int): Any

        @POST("checkin")
        suspend fun setCheckin(@Body customer: Customer)
    }
}