package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class CoursDto(
    val id: Int? = null,
    val id_teacher_fk: TeacherDto? = null,
    val groupe: GroupeDto? = null,
    val module: ModuleDto? = null,
) : java.io.Serializable
