package com.example.logmeet.data.dto.auth.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseAuthSignupResponse(
    @SerialName("result")
    val result: AuthSignupResponse,
)

//data class BaseResponseProjectInfoResult(
//    @SerialName("code")
//    val code: Int,
//    @SerialName("message")
//    val message: String,
//    @SerialName("result")
//    val result: UserId,
//    @SerialName("success")
//    val success: Boolean,
//) {
//    @Serializable
//    data class UserId(
//        @SerialName("userId")
//        val userId: Int,
//    )
//}
