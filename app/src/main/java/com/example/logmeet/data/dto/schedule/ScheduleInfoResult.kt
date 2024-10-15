package com.example.logmeet.data.dto.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleInfoResult(
    @SerialName("projectId")
    val protectId: Int,
    @SerialName("projectName")
    val projectName: String,
    @SerialName("scheduleContent")
    val scheduleContent: String,
    @SerialName("scheduleDate")
    val scheduleDate:String,
    @SerialName("color")
    val color: String
)
