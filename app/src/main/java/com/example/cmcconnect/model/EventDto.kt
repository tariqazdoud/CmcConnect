package com.example.cmcconnect.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class EventDto (
    val id: Int,
    val title: String,
    val date: LocalDate,
    val start_time: LocalTime,
    val end_time: LocalTime,
    val place: String,
    val description: String,
    val poster: String
): java.io.Serializable
