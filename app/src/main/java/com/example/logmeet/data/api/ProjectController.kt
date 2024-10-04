package com.example.logmeet.data.api

import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectCreateResponse
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectInfoResult
import com.example.logmeet.data.dto.project.api_reqeust.ProjectCreateRequest
import com.example.logmeet.data.dto.project.api_response.BaseResponseListProjectListResult
import com.example.logmeet.data.dto.project.api_response.BaseResponseProjectBookmarkResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectController {
    @POST("projects/new")
    fun projectCreate(
        @Header("Authorization")
        authorization: String,
        @Body
        projectCreateRequest : ProjectCreateRequest
    ): Call<BaseResponseProjectCreateResponse>

    @GET("projects/{projectId}")
    fun getProjectDetail(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int
    ): Call<BaseResponseProjectInfoResult>

    @GET("projects/project-list")
    fun getProjectList(
        @Header("Authorization")
        authorization: String,
    ): Call<BaseResponseListProjectListResult>

    @GET("projects/bookmark-list")
    fun getBookmarkList(
        @Header("Authorization")
        authorization: String,
    ): Call<BaseResponseListProjectListResult>

    @PUT("projects/{projectId}/bookmark")
    fun changeBookmark(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int,
    ): Call<BaseResponseProjectBookmarkResult>
}