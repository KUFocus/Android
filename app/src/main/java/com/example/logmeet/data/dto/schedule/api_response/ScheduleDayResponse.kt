package com.example.logmeet.data.dto.schedule.api_response

import com.example.logmeet.data.dto.schedule.ScheduleListResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDayResponse(
    @SerialName("result")
    val result: List<ScheduleListResult>
)
