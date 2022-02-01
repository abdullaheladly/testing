package com.abdullah996.leadscrm.repository.create

import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Query

interface LeadCreateRepo {
    suspend fun createLead(
        company_id:Int?,
         name:String?,
         email: String?,
         notes:String?,
        phone:Array<String>,
         sources:Array<String>

    ):Response<CreateLeadResponse>
}