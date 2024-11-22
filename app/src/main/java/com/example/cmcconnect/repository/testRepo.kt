package com.example.cmcconnect.repository

import com.example.cmcconnect.model.YearDto

interface testRepo {
    suspend fun getYear():YearDto?
}