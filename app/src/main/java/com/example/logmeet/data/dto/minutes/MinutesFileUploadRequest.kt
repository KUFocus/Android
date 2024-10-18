package com.example.logmeet.data.dto.minutes

import com.example.logmeet.domain.entity.FileType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutesFileUploadRequest(
    @SerialName("base64FileData")
    val base64FileData: String,
    @SerialName("fileName")
    val fileName: String,
    @SerialName("fileType")
    val fileType: String,
)
