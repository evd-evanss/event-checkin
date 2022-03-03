package com.sugarspoon.data.model.request

import com.sugarspoon.data.model.entity.CustomerEntity

data class CustomerRequest(
    val eventId: String,
    val name: String,
    val email: String
)

fun CustomerEntity.toRequest() : CustomerRequest {
    return CustomerRequest(
        eventId = eventId,
        name = name,
        email = email
    )
}