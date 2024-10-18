package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDto(
    @SerialName("extractedScheduleDate")
    val extractedScheduleDate: String,
    @SerialName("extractedScheduleContent")
    val extractedScheduleContent: String,
)
