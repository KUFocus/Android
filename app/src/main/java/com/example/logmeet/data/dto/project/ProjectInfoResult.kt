package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectInfoResult(
    @SerialName("projectId")
    val projectId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("content")
    val content: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("userProjects")
    val userProjects: List<UserProjectDto>,
)
