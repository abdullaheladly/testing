package com.abdullah996.leadscrm.repository.actions

import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.getstatus.AllStatusReponse
import com.abdullah996.leadscrm.model.updateaction.AddActionResponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class ActionRepoImpl @Inject constructor(
    private val apis: Apis
):ActionsRepo{
    override suspend fun getStatus(): Response<BaseAction> {
        return apis.getStatus()
    }

    override suspend fun updateStatus(lead_id: String?,  comment: String?,
                                      status_id: String?): Response<AddActionResponse> {
        return apis.updateStatus(lead_id,comment, status_id)
    }

    override suspend fun getAllStatus( lead_id: String?): Response<AllStatusReponse> {
        return apis.getAllStatus( lead_id)
    }

}