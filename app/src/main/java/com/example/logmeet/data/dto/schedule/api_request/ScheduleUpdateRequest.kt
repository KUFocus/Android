package com.example.logmeet.data.dto.schedule.api_request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleUpdateRequest(
    @SerialName("scheduleContent")
    val scheduleContent: String,
    @SerialName("scheduleDate")
    val scheduleDate:String
)
