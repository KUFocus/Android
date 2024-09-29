package com.example.logmeet.data.dto.project.api_response

import com.example.logmeet.data.dto.project.ProjectListResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseListProjectListResult(
    @SerialName("result")
    val result: List<ProjectListResult>,
)
