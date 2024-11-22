package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInDto(
    var id: Int?,
    var name: String?,
    var email: String?,
    var phone: String?,
    var image: String?,
    var id_groupe_fk: Int? = null,
    var id_type_user_fk: Int?,
    var id_pole_fk : Int? = null
) : java.io.Serializable