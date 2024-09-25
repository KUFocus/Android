package com.example.logmeet.data.dto.project.api_response

import com.example.logmeet.data.dto.project.api_response.ProjectCreateResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponseProjectCreateResponse(
    @SerialName("result")
    val result: ProjectCreateResponse,
)
