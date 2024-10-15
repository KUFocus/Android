package com.example.logmeet.data.dto.project.api_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectLeaderDelegationResponse(
    @SerialName("projectId")
    val projectId: Int,
    @SerialName("newLeaderId")
    val newLeaderId: Int,
)
