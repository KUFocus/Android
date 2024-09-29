package com.example.logmeet.data.dto.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectListResult(
    @SerialName("projectId")
    val projectId: Int,
    @SerialName("projectName")
    val projectName: String,
    @SerialName("role")
    val role: String,
    @SerialName("bookmark")
    val bookmark: Boolean,
    @SerialName("projectColor")
    val projectColor: String,
    @SerialName("numOfMember")
    val numOfMember: Int,
    @SerialName("createdAt")
    val createdAt: String,
)