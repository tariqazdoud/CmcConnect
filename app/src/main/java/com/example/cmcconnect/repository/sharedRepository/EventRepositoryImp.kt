package com.example.cmcconnect.repository.sharedRepository

import com.example.cmcconnect.model.EventDto
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventRepositoryImp @Inject constructor(private val postgrest: Postgrest): EventRepository {
    override suspend fun getAllEvents(): List<EventDto>? {
        return try {
            withContext(Dispatchers.IO) {
                val events = postgrest.from("event").select(Columns.ALL) {
                    order("id", order = Order.DESCENDING)
                }.decodeList<EventDto>()
                events
            }
        } catch (e:Exception) {
            null
        }
    }

    override suspend fun getRecentEvents(): List<EventDto>? {
        return try {
            withContext(Dispatchers.IO) {
                val events = postgrest.from("event").select(Columns.ALL) {
                    order("id", order = Order.DESCENDING)
                    limit(4)
                }.decodeList<EventDto>()
                events
            }
        } catch (e:Exception) {
            null
        }
    }

}