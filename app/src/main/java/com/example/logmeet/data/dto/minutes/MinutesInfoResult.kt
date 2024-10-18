package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesInfoResult(
    @SerialName("minutesId")
    val minutesId: Int,
    @SerialName("projectId")
    val projectId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("content")
    val content: String,
    @SerialName("filePath")
    val filePath: String,
    @SerialName("createdAt")
    val createdAt: String,
)
