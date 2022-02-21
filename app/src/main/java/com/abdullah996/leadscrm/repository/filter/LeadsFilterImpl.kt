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
}