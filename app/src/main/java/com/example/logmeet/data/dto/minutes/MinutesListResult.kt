package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesListResult(
    @SerialName("minutesId")
    val minutesId: Int,
    @SerialName("projectId")
    val projectId: Int,
    @SerialName("minutesName")
    val minutesName: String,
    @SerialName("color")
    var color: String?,
    @SerialName("type")
    val type: String,
    @SerialName("status")
    val status: String,
    @SerialName("createdAt")
    val createdAt: String,
)
