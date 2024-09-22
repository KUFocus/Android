package com.example.logmeet.data.dto.auth

import com.google.android.material.textfield.TextInputEditText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLogin(
    @SerialName("email")
    var email: String,
    @SerialName("password")
    var password: String,
)