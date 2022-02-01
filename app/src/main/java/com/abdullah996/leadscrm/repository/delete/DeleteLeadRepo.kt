package com.abdullah996.leadscrm.repository.delete

import com.abdullah996.leadscrm.model.delete.DeleteLeadResponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Query

interface DeleteLeadRepo {
    suspend fun deleteLead(
         company_id:Int?,
         lead_id: String?
    ): Response<DeleteLeadResponse>
}