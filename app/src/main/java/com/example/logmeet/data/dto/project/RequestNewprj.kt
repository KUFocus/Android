package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNewprj(
    @SerialName("name")
    val name: String,
    @SerialName("content")
    val content: String,
    @SerialName("color")
    val color: String,
)
