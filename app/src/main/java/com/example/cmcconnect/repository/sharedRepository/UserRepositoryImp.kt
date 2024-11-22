package com.example.cmcconnect.repository.sharedRepository

import com.example.cmcconnect.model.*
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : UserRepository {
    override suspend fun getUserInDto(userEmail: String): UserDto {
        return withContext(Dispatchers.IO) {


            val userDto = postgrest.from("user").select {
                filter {
                    eq("user_email", userEmail)
                }
            }.decodeSingle<UserDto>()
            userDto

        }
    }


    override suspend fun getUserInfo(userEmail: String, typeId: Int): UserInDto {
        return withContext(Dispatchers.IO) {
            when (typeId) {
                1 -> {
                    val studentDetails = postgrest.from("student").select(columns = Columns
                        .list("id,name,email,phone,image,id_groupe_fk,id_type_user_fk")) {
                        filter {
                            eq("email", userEmail)
                        }
                    }.decodeSingle<UserInDto>()
                    studentDetails
                }

                2 -> {
                    val teacherDetails = postgrest.from("teacher").select(columns = Columns
                        .list("id,name,email,phone,image,id_type_user_fk")) {
                        filter {
                            eq("email", userEmail)
                        }
                    }.decodeSingle<UserInDto>()
                    teacherDetails
                }

                3 -> {
                    val adminDetails = postgrest.from("admin").select(columns = Columns
                        .list("id,name,email,phone,image,id_pole_fk,id_type_user_fk")) {
                        filter {
                            eq("email", userEmail)
                        }
                    }.decodeSingle<UserInDto>()
                    adminDetails
                }

                else -> throw Exception("User role not found")
            }
        }
    }


}

