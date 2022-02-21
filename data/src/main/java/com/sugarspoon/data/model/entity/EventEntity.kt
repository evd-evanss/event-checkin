package com.sugarspoon.data.model.entity

import android.os.Parcelable
import com.sugarspoon.data.model.response.EventResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventEntity (
    val date: Number,
    val description: String,
    val image: String,
    val longitude: Number,
    val latitude: Number,
    val price: Number,
    val title: String,
    val id: String
): Parcelable

fun EventResponse.toEventEntity() = EventEntity(
    date = date ?: 0,
    description = description.orEmpty(),
    image = image.orEmpty(),
    longitude = longitude ?: 0,
    latitude = latitude ?: 0,
    price = price ?: 0,
    title = title.orEmpty(),
    id = id.orEmpty()
)
