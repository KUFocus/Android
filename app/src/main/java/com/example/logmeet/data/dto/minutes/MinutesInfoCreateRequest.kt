package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesInfoCreateRequest(
    @SerialName("minutesId")
    val minutesId: Int,
    @SerialName("minutesName")
    val minutesName: String,
    @SerialName("projectId")
    val projectId: Int,
)
