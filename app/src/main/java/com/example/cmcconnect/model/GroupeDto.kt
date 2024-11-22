package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupeDto(
    val id: Int? = null,
    val name: String? = null,
    val id_year_fk: Int? = null,
    val filiere: FiliereDto? = null
) : java.io.Serializable
