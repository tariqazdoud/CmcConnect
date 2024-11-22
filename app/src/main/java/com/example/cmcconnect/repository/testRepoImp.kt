package com.example.cmcconnect.repository

import com.example.cmcconnect.model.YearDto
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class testRepoImp @Inject constructor(private val postgrest: Postgrest):testRepo{
    override suspend fun getYear(): YearDto? {
        return try {
            val res = postgrest.from("year").select().decodeSingle<YearDto>()
            res
        }catch (e:Exception){
            null
        }
    }
}