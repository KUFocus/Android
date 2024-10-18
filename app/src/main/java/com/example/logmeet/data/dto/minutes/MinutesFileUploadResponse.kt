package com.example.logmeet.data.dto.minutes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesFileUploadResponse(
    @SerialName("filePath")
    val filePath: String,
    @SerialName("fileType")
    val fileType: String,
)
