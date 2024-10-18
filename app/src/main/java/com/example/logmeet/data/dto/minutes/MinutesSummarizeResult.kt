package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesSummarizeResult(
    @SerialName("summarizedText")
    val summarizedText: String,
    @SerialName("schedules")
    val schedules: List<ScheduleDto>,
)
