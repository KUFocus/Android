package com.example.logmeet.data.dto.auth.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseAuthLoginResponse(
    @SerialName("result")
    val result: AuthLoginResponse,
)

//data class ResponseLogin(
//    @SerialName("code")
//    val code: Int,
//    @SerialName("message")
//    val message: String,
//    @SerialName("result")
//    val result: Token,
//    @SerialName("success")
//    val success: Boolean,
//) {
//    @Serializable
//    data class Token(
//        @SerialName("accessToken")
//        val accessToken: String,
//        @SerialName("refreshToken")
//        val refreshToken: String,
//    )
//}