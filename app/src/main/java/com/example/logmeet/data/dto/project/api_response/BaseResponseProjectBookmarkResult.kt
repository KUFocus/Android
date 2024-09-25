package com.example.logmeet.data.dto.project.api_response

import com.example.logmeet.data.dto.project.ProjectBookmarkResult
import kotlinx.serialization.SerialName

data class BaseResponseProjectBookmarkResult(
    @SerialName("result")
    val result: ProjectBookmarkResult,
)
