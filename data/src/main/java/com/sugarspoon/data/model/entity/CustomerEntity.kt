package com.sugarspoon.data.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sugarspoon.data.model.response.CustomerResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerEntity (
    @SerializedName("eventId")
    val eventId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
): Parcelable

fun CustomerResponse.toCustomerEntity() {
    CustomerEntity(
        eventId = eventId.orEmpty(),
        name = name.orEmpty(),
        email = email.orEmpty()
    )
}