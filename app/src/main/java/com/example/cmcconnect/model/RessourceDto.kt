package com.example.cmcconnect.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class RessourceDto (
    val id: Int,
    val title: String,
    val file: String,
    val type: String,
    val pubDate: LocalDate,
    val id_module_fk: Int,
    val id_teacher_fk: Int
): java.io.Serializable
