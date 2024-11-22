package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class FiliereDto (
    val id: Int? = null,
    val name: String? = null,
    val id_pole_fk: Int? = null
): java.io.Serializable
