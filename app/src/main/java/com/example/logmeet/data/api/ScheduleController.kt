package com.example.logmeet.data.api

import com.example.logmeet.data.dto.BaseResponseVoid
import com.example.logmeet.data.dto.schedule.ScheduleInfoResult
import com.example.logmeet.data.dto.schedule.api_request.ScheduleUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

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
}