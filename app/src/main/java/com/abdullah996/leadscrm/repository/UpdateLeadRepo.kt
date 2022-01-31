package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Query

interface UpdateLeadRepo {
    suspend fun updateLead(
         company_id:Int?,
         lead_id: String?,
         name:String?,
         email: String?,
         notes:String?,
        phone:Array<String>,
         is_qualified :String,
         reasons :String,
    ):Response<UpdateLeadsRespons>
}