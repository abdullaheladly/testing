package com.abdullah996.leadscrm.repository.filter

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import retrofit2.Response

interface LeadsFilters {
    suspend fun filterByTag(
        tag:String
    ): Response<LeedsReponse>

    suspend fun filterByType(
        tag:String
    ): Response<LeedsReponse>

    suspend fun filterByInterest(
        tag:String
    ): Response<LeedsReponse>

    suspend fun filterByRequestInterest(
        tag:String
    ): Response<LeedsReponse>

    suspend fun filterBySourceId(
        id:Int
    ): Response<LeedsReponse>
    suspend fun filterByUnitTypeId(
        id:Int
    ): Response<LeedsReponse>
    suspend fun filterByBudget(
        from:Long,
        to:Long
    ): Response<LeedsReponse>
}