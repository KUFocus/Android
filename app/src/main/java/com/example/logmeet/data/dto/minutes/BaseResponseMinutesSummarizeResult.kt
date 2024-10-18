package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseMinutesSummarizeResult(
    @SerialName("result")
    val result: MinutesSummarizeResult,
)
