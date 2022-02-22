package com.sugarspoon.data.sources

import com.google.gson.Gson
import com.sugarspoon.data.RetrofitServiceFactory
import com.sugarspoon.data.model.entity.CustomerEntity
import com.sugarspoon.data.model.response.CustomerResponse
import com.sugarspoon.data.model.response.EventResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.*
import javax.inject.Inject

class EventsDataSource @Inject constructor(
    retrofit: Retrofit.Builder,
    okHttpClient: OkHttpClient
) {

    private val service =
        RetrofitServiceFactory(retrofit, okHttpClient).newInstance<Service>()

    fun getEvents() = flow {
        emit(service.getEvents())
    }

    fun getEventsDetail(id: String) = flow {
        emit(service.getEventsDetail(id))
    }

    fun setCheckin(customer: CustomerEntity) = flow {
        emit(
            service.setCheckin(customer)
        )
    }

    interface Service {

        @GET("events")
        suspend fun getEvents(): List<EventResponse>

        @GET("events/{id}")
        suspend fun getEventsDetail(@Path("id") id: String): EventResponse

        @Headers("Content-Type: application/json")
        @POST("checkin")
        suspend fun setCheckin(@Body customer: CustomerEntity): Any

    }
}