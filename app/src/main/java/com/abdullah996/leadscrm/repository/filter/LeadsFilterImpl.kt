package com.abdullah996.leadscrm.repository.filter

import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class LeadsFilterImpl@Inject constructor(
    private val apis: Apis
): LeadsFilters {

    override suspend fun filterByTag(tag: String): Response<LeedsReponse> {
        return apis.filterByTag(tag)
    }

    override suspend fun filterByType(tag: String): Response<LeedsReponse> {
        return apis.filterByType(tag)
    }

    override suspend fun filterByInterest(tag: String): Response<LeedsReponse> {
        return apis.filterByInterest(tag)
    }

    override suspend fun filterByRequestInterest(tag: String): Response<LeedsReponse> {
        return apis.filterByRequestInterest(tag)
    }

    override suspend fun filterBySourceId(id: Int): Response<LeedsReponse> {
        return apis.filterBySourceID(id)
    }

    override suspend fun filterByUnitTypeId(id: Int): Response<LeedsReponse> {
        return apis.filterByUnitTypeId(id)
    }

    override suspend fun filterByBudget(from: Int, to: Int): Response<LeedsReponse> {
        return apis.filterByBudget(from, to)
    }
}