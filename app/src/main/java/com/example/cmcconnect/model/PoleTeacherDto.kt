package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class PoleTeacherDto (
    val id: Int? =null,
    val pole: PoleDto? = null,
    val teacher: TeacherDto? = null
): java.io.Serializable
