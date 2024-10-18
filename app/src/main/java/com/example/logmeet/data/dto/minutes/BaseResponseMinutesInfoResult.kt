package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseMinutesInfoResult(
    @SerialName("result")
    val result: MinutesInfoResult,
)
