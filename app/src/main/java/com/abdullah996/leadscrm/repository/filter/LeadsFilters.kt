package com.abdullah996.leadscrm.repository.filter

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import retrofit2.Response

interface LeadsFilters {
    suspend fun filterByTag(
        tag:String
    ): Response<LeedsReponse>
}