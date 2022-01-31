package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class LoginRepoImpl @Inject constructor(
    private val apis: Apis
):LoginRepo {
    override suspend fun login(email: String, password: String, notificationToken: String?):Response<UserResponse> {
        return apis.userLogin(email, password, notificationToken)
    }

    override suspend fun logout(
        company_id: Int?,
        notificationToken: String?
    ): Response<UpdateLeadsRespons> {
        return apis.logout(company_id, notificationToken)
    }

}