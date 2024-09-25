package com.example.logmeet.data.dto.auth.api_request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginRequest(
    @SerialName("email")
    var email: String,
    @SerialName("password")
    var password: String,
)