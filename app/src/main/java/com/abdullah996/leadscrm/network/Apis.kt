package com.abdullah996.leadscrm.network

import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.model.delete.DeleteLeadResponse
import com.abdullah996.leadscrm.model.getstatus.AllStatusReponse
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.notifications.NotificationsResponse
import com.abdullah996.leadscrm.model.statusmodel.StatusModelResponse
import com.abdullah996.leadscrm.model.unreadnotifications.UnreadNotificationsResponse
import com.abdullah996.leadscrm.model.updateaction.AddActionResponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
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
    ):Response<UserResponse>




    // get all leads by company id and page
    @GET("lead/get-all")
    suspend fun getAllLeads(
        @Query("is_paginate") num:Int,
        @Query("company_id") company_id:Int?,
        @Query("page") page: Int,
        @Header("Authorization") token:String,

    ):Response<LeedsReponse>

    // get all leads by company id and page
    @GET("lead/get-all")
    suspend fun getAllLeadsByStatus(
        @Query("is_paginate") num:Int,
        @Query("company_id") company_id:Int?,
        @Query("status_id") status_id: Int?,
        ):Response<LeedsReponse>

    @GET("lead/get-all")
    suspend fun getAllLeadsByStatus(
        @Query("is_paginate") num:Int,
        @Query("company_id") company_id:Int?,
        @Query("status_id") status_id: Int?,
        @Query("page") page: Int,
    ):Response<LeedsReponse>


    //search for lead by name
    @GET("lead/get-all")
    suspend fun searchByName(
        @Query("is_paginate") num:Int,
        @Query("search") search:String,
        @Query("company_id") company_id:Int?,
        @Header("Authorization") token:String,
    ):Response<LeedsReponse>


    //update already exist  lead
    @POST("lead/update")
    @FormUrlEncoded
    suspend fun updateLead(
        @Query("company_id") company_id:Int?,
        @Field("lead_id") lead_id: String?,
        @Field("name") name:String?,
        @Field("email") email: String?,
        @Field("notes") notes:String?,
        @Field("phones[]") phone:Array<String>  ,
        @Field("is_qualified") is_qualified :String,
        @Field("reason") reasons :String,
    ):Response<UpdateLeadsRespons>


    //create new lead
    @POST("lead/create")
    @FormUrlEncoded
    suspend fun createLead(
        @Query("company_id") company_id:Int?,
        @Field("name") name:String?,
        @Field("email") email: String?,
        @Field("notes") notes:String?,
        @Field("phones[]") phone:Array<String>,
        @Field("sources[]") sources:Array<String>
    ):Response<CreateLeadResponse>

    //delete current lead by id
    @POST("lead/delete")
    @FormUrlEncoded
    suspend fun deleteLead(
        @Query("company_id") company_id:Int?,
        @Field("lead_id") lead_id: String?
    ):Response<DeleteLeadResponse>


    //logout
    @POST("user/logout")
    @FormUrlEncoded
    suspend fun logout(
        @Query("company_id") company_id:Int?,
        @Field("firebase_token") notificationToken: String?
    ):Response<UpdateLeadsRespons>


    // get all action available status
    @GET("lead-statuses")
    suspend fun getStatus(
    ):Response<BaseAction>

    // get all action available status
    @GET("lead-statuses/stats")
    suspend fun getAllStatusToFilter(
        @Query("company_id") company_id:Int?,
    ):Response<StatusModelResponse>

    //create new action for the lead
    @POST("lead/status")
    @FormUrlEncoded
    suspend fun updateStatus(
        @Field("lead_id") lead_id: String?,
        @Field("comment") comment: String?,
        @Field("status_id") status_id: String?
    ):Response<AddActionResponse>



    // get all actions for the current lead
    @GET("lead/status")
    suspend fun getAllStatus(
        @Query("lead_id") lead_id: String?
    ):Response<AllStatusReponse>



    //get all notification
    @GET("user/notification/get-all")
    suspend fun getAllNotifications(
    ):Response<NotificationsResponse>




    //mark all notifications as read
    @GET("user/notification/mark-as-read")
    suspend fun markAllAsRead():Response<UpdateLeadsRespons>

    //totalUnreadNotification
    @GET("user/notification/total-unread-notifications")
    suspend fun getAllUnreadNotifications():Response<UnreadNotificationsResponse>


    //filter the leads by date
    @GET("lead/filter-by-date")
    suspend fun filterByData(
        @Query("year") year:String,
        @Query("months[]") month: Array<String>
    ):Response<LeedsReponse>



    // filter by tags
    @GET("lead/filter")
    suspend fun filterByTag(
        @Query("tag") tag: String
    ):Response<LeedsReponse>

    // filter by type
    @GET("lead/filter")
    suspend fun filterByType(
        @Query("type") type: String
    ):Response<LeedsReponse>

    // filter by interest
    @GET("lead/filter")
    suspend fun filterByInterest(
        @Query("interested_type") type: String
    ):Response<LeedsReponse>

    // filter by request interest
    @GET("lead/filter")
    suspend fun filterByRequestInterest(
        @Query("request_interest") type: String
    ):Response<LeedsReponse>


    // filter by source id
    @GET("lead/filter")
    suspend fun filterBySourceID(
        @Query("source_id") id: Int
    ):Response<LeedsReponse>

    // filter by budget
    @GET("lead/filter")
    suspend fun filterByBudget(
        @Query("budget_from") from: Long,
        @Query("budget_to") to: Long
    ):Response<LeedsReponse>

    // filter by unit Type id
    @GET("lead/filter")
    suspend fun filterByUnitTypeId(
        @Query("unit_type_id") id: Int
    ):Response<LeedsReponse>








}