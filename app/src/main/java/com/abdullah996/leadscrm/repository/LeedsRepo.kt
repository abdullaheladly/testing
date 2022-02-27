package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.unreadnotifications.UnreadNotificationsResponse
import retrofit2.Response

interface LeedsRepo {
    suspend fun getAllLeads(
        is_paginate:Int,
        search:String,
        company_id:Int?,
        token:String
    ):Response<LeedsReponse>

    suspend fun getAllLeads(
        is_paginate:Int,
        search:String,
        company_id:Int?,
        token:String,
        page:Int
    ):Response<LeedsReponse>
    suspend fun searchByName(
        is_paginate:Int,
        search:String,
        company_id:Int?,
        token:String
    ):Response<LeedsReponse>

    suspend fun filterByDate(
        year:String,
        month: Array<String>
    ):Response<LeedsReponse>

    suspend fun getAllUnreadNotifications(

    ):Response<UnreadNotificationsResponse>
}