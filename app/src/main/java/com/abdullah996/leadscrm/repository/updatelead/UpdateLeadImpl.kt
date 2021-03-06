package com.abdullah996.leadscrm.repository.updatelead

import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class UpdateLeadImpl @Inject constructor(
    private val apis: Apis
) : UpdateLeadRepo {
    override suspend fun updateLead(
        company_id: Int?,
        lead_id: String?,
        name: String?,
        email: String?,
        notes: String?,
        phone: Array<String>,
        is_qualified: String,
        reasons: String,
    ): Response<UpdateLeadsRespons> {
       return apis.updateLead(company_id, lead_id, name, email, notes ,phone, is_qualified, reasons)
    }
}