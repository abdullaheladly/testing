package com.abdullah996.leadscrm.repository.create

import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class LeadsCreateRepoImpl  @Inject constructor(
    private val apis: Apis
):LeadCreateRepo {
    override suspend fun createLead(
        company_id: Int?,
        name: String?,
        email: String?,
        notes: String?,
        phone: Array<String>,
         sources:Array<String>
    ) :Response<CreateLeadResponse>{
       return apis.createLead(company_id, name, email, notes, phone,sources)
    }
}