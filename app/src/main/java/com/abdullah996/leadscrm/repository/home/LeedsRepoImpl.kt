package com.abdullah996.leadscrm.repository.home

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.statusmodel.StatusModelResponse
import com.abdullah996.leadscrm.model.unreadnotifications.UnreadNotificationsResponse
import com.abdullah996.leadscrm.network.Apis
import com.abdullah996.leadscrm.repository.home.LeedsRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class LeedsRepoImpl@Inject constructor(
    private val apis: Apis
): LeedsRepo {
    override suspend fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token:String):Response<LeedsReponse> {
        return apis.getAllLeads(is_paginate,company_id,1,"Bearer $token")
    }
    override suspend fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token:String,page:Int):Response<LeedsReponse> {
        return apis.getAllLeads(is_paginate,company_id,page,"Bearer $token")
    }

    override suspend fun searchByName(
        is_paginate: Int,
        search: String,
        company_id: Int?,
        token: String
    ): Response<LeedsReponse> {
        return apis.searchByName(is_paginate,search,company_id, token)
    }

    override suspend fun filterByDate(year: String, month: Array<String>): Response<LeedsReponse> {
       return apis.filterByData(year, month)
    }

    override suspend fun getAllUnreadNotifications(): Response<UnreadNotificationsResponse> {
        return apis.getAllUnreadNotifications()
    }

    override suspend fun getAllLeadsByStatusId(
        is_paginate: Int,
        company_id: Int?,
        statusId:Int
    ): Response<LeedsReponse> {
        return apis.getAllLeadsByStatus(is_paginate,company_id,statusId)
    }

    override suspend fun getAllLeadsByStatusId(
        is_paginate: Int,
        company_id: Int?,
        statusId:Int,
        page: Int
    ): Response<LeedsReponse> {
        return apis.getAllLeadsByStatus(is_paginate,company_id,statusId,page)
    }

    override suspend fun getAllStatusToFilter(company_id: Int?): Response<StatusModelResponse> {
        return apis.getAllStatusToFilter(company_id)
    }

}