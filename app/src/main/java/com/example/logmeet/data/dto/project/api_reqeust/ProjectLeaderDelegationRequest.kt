package com.example.logmeet.data.dto.project.api_reqeust

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectLeaderDelegationRequest(
    @SerialName("newLeaderId")
    val newLeaderId: Int,
)