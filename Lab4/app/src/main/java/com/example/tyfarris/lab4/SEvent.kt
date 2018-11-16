package com.example.tyfarris.lab4

data class EventResponse(
        val event : String = "",
        val date : String = "",
        val time : String = "",
        val location: String = "")

fun EventResponse.mapToEvent() = MyViewModel.scheduledEvent(event, date, time, location)