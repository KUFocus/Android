package com.example.logmeet.data.dto.project.api_reqeust

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectUpdateReqeust(
    @SerialName("name")
    val name: String,
    @SerialName("content")
    val content: String,
    @SerialName("color")
    val color: String,
)
