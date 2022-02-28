package com.abdullah996.leadscrm.repository.updatelead

import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import retrofit2.Response

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