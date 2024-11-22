package com.example.cmcconnect.repository.sharedRepository

import com.example.cmcconnect.model.UserInInfo

interface AuthenticationRepository {
    suspend fun signIn(email:String, password:String): Boolean
    suspend fun getCurrentUserEmail(): String
    suspend fun logout(): Boolean

}