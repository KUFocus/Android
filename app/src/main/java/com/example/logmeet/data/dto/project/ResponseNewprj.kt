package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseNewprj(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: ProjectId,
    @SerialName("success")
    val success: Boolean,
) {
    @Serializable
    data class ProjectId(
        @SerialName("projectId")
        val projectId: Int,
    )
}
