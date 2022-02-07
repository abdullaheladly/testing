package com.abdullah996.leadscrm.repository.delete

import com.abdullah996.leadscrm.model.delete.DeleteLeadResponse
import retrofit2.Response

interface DeleteLeadRepo {
    suspend fun deleteLead(
         company_id:Int?,
         lead_id: String?
    ): Response<DeleteLeadResponse>
}