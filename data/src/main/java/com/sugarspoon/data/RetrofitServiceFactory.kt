package com.sugarspoon.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal class RetrofitServiceFactory(
    val retrofitBuilder: Retrofit.Builder,
    val okHttpClient: OkHttpClient
) {

    inline fun <reified Service> newInstance(): Service {
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(Service::class.java)
    }
}