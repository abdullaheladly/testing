package com.abdullah996.leadscrm.network

import com.abdullah996.leadscrm.model.agent.AgentResponse
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.*


interface Apis {
    @POST("user/login")
    @FormUrlEncoded
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("firebase_token") notificationToken: String?
    ):UserResponse


    @GET("agent/get-all")

    suspend fun getAllAgent(
        @Query("is_paginate") num:Int,
        @Query("search") search:String,
        @Query("company_id") company_id:Int?
    ):AgentResponse

    @GET("lead/get-all")

    suspend fun getAllLeads(

        @Query("is_paginate") num:Int,
        @Query("search") search:String,
        @Query("company_id") company_id:Int?,
        @Header("Authorization") token:String,
    ):LeedsReponse


}