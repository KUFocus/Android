package com.example.logmeet.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignup(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("userName")
    val userName: String,
)
