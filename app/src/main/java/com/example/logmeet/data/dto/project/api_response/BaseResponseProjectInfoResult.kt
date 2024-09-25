package com.example.logmeet.data.dto.project.api_response

import com.example.logmeet.data.dto.project.ProjectInfoResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseProjectInfoResult(
    @SerialName("result")
    val result: ProjectInfoResult,
)
