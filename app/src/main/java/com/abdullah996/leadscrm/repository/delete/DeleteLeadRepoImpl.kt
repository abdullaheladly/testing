package com.abdullah996.leadscrm.repository.delete

import com.abdullah996.leadscrm.model.delete.DeleteLeadResponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class DeleteLeadRepoImpl@Inject constructor(
    private val apis: Apis
):DeleteLeadRepo {
    override suspend fun deleteLead(
        company_id: Int?,
        lead_id: String?
    ): Response<DeleteLeadResponse> {
        return apis.deleteLead(company_id, lead_id)
    }
}