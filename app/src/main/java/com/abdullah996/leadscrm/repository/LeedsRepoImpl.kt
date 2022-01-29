package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class LeedsRepoImpl@Inject constructor(
    private val apis: Apis
):LeedsRepo {
    override suspend fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token:String):LeedsReponse {
        return apis.getAllLeads(is_paginate,search,company_id,"Bearer $token")
    }

}