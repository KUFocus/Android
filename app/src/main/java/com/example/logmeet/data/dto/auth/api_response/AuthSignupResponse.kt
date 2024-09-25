package com.example.logmeet.data.dto.auth.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthSignupResponse(
    @SerialName("userId")
    val userId: Int,
)
