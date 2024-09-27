package com.example.logmeet.data.api

import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectCreateResponse
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.data.dto.project.api_reqeust.ProjectCreateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectController {
    @POST("project/new")
    fun projectCreate(
        @Header("Authorization")
        authorization: String,
        @Body
        projectCreateRequest : ProjectCreateRequest
    ): Call<BaseResponseProjectCreateResponse>

    @GET("project/{projectId}")
    fun getProjectDetail(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int
    ): Call<BaseResponseProjectInfoResult>
}