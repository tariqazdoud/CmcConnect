package com.example.cmcconnect.repository.sharedRepository

import android.util.Log
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(private val auth: Auth): AuthenticationRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWith(Email){
                this.email = email
                this.password = password
            }
            true
        }catch (e:Exception){
            false
        }
    }

    override suspend fun getCurrentUserEmail(): String {
        return auth.retrieveUserForCurrentSession(updateSession = true).email.toString()
    }

    override suspend fun logout(): Boolean {
        return try {
            auth.signOut()
            true
        } catch (e: Exception) {
            Log.e("LogoutError", "Failed to log out", e)
            false
        }
    }
}