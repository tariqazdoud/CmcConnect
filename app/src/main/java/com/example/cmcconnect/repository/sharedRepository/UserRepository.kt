package com.example.cmcconnect.repository.sharedRepository

import com.example.cmcconnect.model.UserDto
import com.example.cmcconnect.model.UserInDto
import com.example.cmcconnect.model.UserInInfo

interface UserRepository {
    suspend fun  getUserInDto(userEmail: String) : UserDto
    suspend fun getUserInfo(userEmail: String,typeId : Int): UserInDto

}