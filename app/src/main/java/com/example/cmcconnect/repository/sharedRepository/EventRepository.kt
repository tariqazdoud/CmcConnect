package com.example.cmcconnect.repository.sharedRepository

import com.example.cmcconnect.model.EventDto
import com.example.cmcconnect.model.UserDto

interface EventRepository {
    suspend fun getAllEvents(): List<EventDto>?
    suspend fun getRecentEvents(): List<EventDto>?
}