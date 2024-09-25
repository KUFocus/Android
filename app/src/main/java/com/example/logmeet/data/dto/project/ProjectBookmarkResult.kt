package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectBookmarkResult(
    @SerialName("bookmark")
    val bookmark: Boolean,
)
