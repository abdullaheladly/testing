package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.leeds.LeedsReponse

interface LeedsRepo {
    suspend fun getAllLeads(
        is_paginate:Int,
        search:String,
        company_id:Int?,
        token:String
    ):LeedsReponse
}