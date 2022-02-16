package com.sugarspoon.data.model

data class Event(
    val date: Int,
    val description: String,
    val image: String,
    val longitude: Int,
    val latitude: Int,
    val price: Number,
    val title: String,
    val id: String
)
