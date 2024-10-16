package com.example.logmeet.data.dto.schedule.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleCreateResponse(
    @SerialName("scheduleId")
    val scheduleId: Int
)
