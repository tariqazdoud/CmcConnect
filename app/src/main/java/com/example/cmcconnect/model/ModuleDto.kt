package com.example.cmcconnect.model

import kotlinx.serialization.Serializable

@Serializable
data class ModuleDto (
    val id: Int,
    val name: String
): java.io.Serializable {
    override fun toString(): String {
        return name
    }
}
