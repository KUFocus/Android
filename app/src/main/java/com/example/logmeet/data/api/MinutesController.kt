package com.example.logmeet.data.api

import com.example.logmeet.data.dto.BaseResponseVoid
import com.example.logmeet.data.dto.minutes.BaseResponseListMinutesListResult
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesCreateResponse
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesFileUploadResponse
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesInfoResult
import com.example.logmeet.data.dto.minutes.BaseResponseMinutesSummarizeResult
import com.example.logmeet.data.dto.minutes.MinutesFileUploadRequest
import com.example.logmeet.data.dto.minutes.MinutesInfoCreateRequest
import com.example.logmeet.data.dto.minutes.MinutesManuallyCreateRequest
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MinutesController {
    @PUT("minutes/update-info")
    fun updateMinutesInfo(
        @Header("Authorization")
        authorization: String,
        @Body
        minutesInfoCreateRequest: MinutesInfoCreateRequest
    ): Call<BaseResponseMinutesCreateResponse>

    @POST("minutes/{minutesId}/summarize-text")
    fun createSummarize(
        @Header("Authorization")
        authorization: String,
        @Path("minutesId")
        minutesId: Int,
    ): Call<BaseResponseMinutesSummarizeResult>

    @POST("minutes/upload-file")
    fun createMinutesFile(
        @Header("Authorization")
        authorization: String,
        @Body
        minutesFileUploadRequest: MinutesFileUploadRequest
    ): Call<BaseResponseMinutesFileUploadResponse>

    @POST("minutes/upload-content")
    fun createMinutesManual(
        @Header("Authorization")
        authorization: String,
        @Body
        minutesManuallyCreateRequest: MinutesManuallyCreateRequest
    ): Call<BaseResponseMinutesCreateResponse>

    @GET("minutes/{projectId}/minutes-list")
    fun getProjectMinutesList(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int
    ): Call<BaseResponseListMinutesListResult>

    @GET("minutes/{minutesId}")
    fun getMinutesDetail(
        @Header("Authorization")
        authorization: String,
        @Path("minutesId")
        minutesId: Int
    ): Call<BaseResponseMinutesInfoResult>

    @DELETE("minutes/{minutesId}")
    fun deleteMinutes(
        @Header("Authorization")
        authorization: String,
        @Path("minutesId")
        minutesId: Int
    ): Call<BaseResponseVoid>

    @GET("minutes/minutes-list")
    fun getUserMinutesList(
        @Header("Authorization")
        authorization: String
    ): Call<BaseResponseListMinutesListResult>
}