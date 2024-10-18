package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesManuallyCreateRequest(
    @SerialName("minutesName")
    val minutesName: String,
    @SerialName("textContent")
    val textContent: String,
    @SerialName("projectId")
    val projectId: Int,
)
