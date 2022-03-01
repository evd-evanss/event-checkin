package com.sugarspoon.data.model.entity

import android.content.Context
import android.location.Geocoder
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class EventShareEntity(val content: String)

fun EventEntity.toEventShareEntity(context: Context): EventShareEntity {
    return EventShareEntity(
        content = "$title " +
                "\n$description " +
                "\n\nData: ${date.toLocalDate()} " +
                "\n\nLocal: ${getAddress(context, latitude.toDouble(), longitude.toDouble())}" +
                "\n\nInvestimento: R$ $price",
    )
}

private fun getAddress(
    context: Context,
    latitude: Double,
    longitude: Double
) : String {
    return Geocoder(
        context,
        Locale.getDefault()
    ).getFromLocation(
        latitude,
        longitude,
        1
    )[0].getAddressLine(0)
}

fun Number.toLocalDate(): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val netDate = Date(Timestamp(toLong()).time)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}