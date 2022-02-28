package com.abdullah996.leadscrm.repository.home

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.statusmodel.StatusModelResponse
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


    suspend fun getAllLeadsByStatusId(
        is_paginate:Int,
        company_id:Int?,
        statusId:Int
    ):Response<LeedsReponse>

    suspend fun getAllLeadsByStatusId(
        is_paginate:Int,
        company_id:Int?,
        statusId:Int,
        page: Int
    ):Response<LeedsReponse>

    suspend fun getAllStatusToFilter(
        company_id:Int?,
    ):Response<StatusModelResponse>
}