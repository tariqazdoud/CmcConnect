package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupToPost(
    val name : String,
    val id_year_fk : Int,
    val id_filiere_fk : Int
): java.io.Serializable