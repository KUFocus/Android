package com.example.logmeet.data.api

import com.example.logmeet.data.dto.BaseResponseVoid
import com.example.logmeet.data.dto.schedule.ScheduleInfoResult
import com.example.logmeet.data.dto.schedule.ScheduleListResult
import com.example.logmeet.data.dto.schedule.api_request.ScheduleCreateRequest
import com.example.logmeet.data.dto.schedule.api_request.ScheduleUpdateRequest
import com.example.logmeet.data.dto.schedule.api_response.ScheduleCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleController {
    @GET("schedule/{scheduleId}")
    fun getScheduleDetail(
        @Header("Authorization")
        authorization: String,
        @Path("scheduleId")
        scheduleId: Int
    ): Call<ScheduleInfoResult>

    @PUT("schedule/{scheduleId}")
    fun updateScheduleDetail(
        @Header("Authorization")
        authorization: String,
        @Path("scheduleId")
        scheduleId: Int,
        @Body
        scheduleUpdateRequest: ScheduleUpdateRequest
    ): Call<BaseResponseVoid>

    @DELETE("schedule/{scheduleId}")
    fun deleteScheduleDetail(
        @Header("Authorization")
        authorization: String,
        @Path("scheduleId")
        scheduleId: Int
    ): Call<BaseResponseVoid>

    @POST("schedule/new")
    fun createSchedule(
        @Header("Authorization")
        authorization: String,
        @Body
        scheduleCreateRequest: ScheduleCreateRequest
    ): Call<ScheduleCreateResponse>

    @GET("schedule/{projectId}/schedules") //프로젝트 특정날짜
    fun getProjectDaySchedule(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int,
        @Query("date")
        date: String
    ): Call<List<ScheduleListResult>>

    @GET("schedule/{projectId}/schedule-list") //프로젝트 특정 달
    fun getProjectMonthlySchedule(
        @Header("Authorization")
        authorization: String,
        @Path("projectId")
        projectId: Int,
        @Query("yearMonth")
        yearMonth: String
    ): Call<ScheduleListResult>

    @GET("schedule/users/schedules") //사용자 특정날짜
    fun getUsersDaySchedule(
        @Header("Authorization")
        authorization: String,
        @Query("date")
        date: String
    ): Call<ScheduleListResult>

    @GET("schedule/users/schedule-list") //사용자 특정 달
    fun getUsersMonthlySchedule(
        @Header("Authorization")
        authorization: String,
        @Query("yearMonth")
        yearMonth: String
    ): Call<ScheduleListResult>
}