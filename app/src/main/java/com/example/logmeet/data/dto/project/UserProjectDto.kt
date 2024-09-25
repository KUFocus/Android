package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProjectDto(
    @SerialName("id")
    val id: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("userName")
    val userName: String,
    @SerialName("role")
    val role: String,
    @SerialName("bookmark")
    val bookmark: Boolean,
    @SerialName("color")
    val color: String,
)