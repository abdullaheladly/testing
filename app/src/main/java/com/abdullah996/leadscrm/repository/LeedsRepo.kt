package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import retrofit2.Response
import java.time.Month

interface LeedsRepo {
    suspend fun getAllLeads(
        is_paginate:Int,
        search:String,
        company_id:Int?,
        token:String
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
}