package com.abdullah996.leadscrm.repository.login

import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.model.user.UserResponse
import retrofit2.Response

interface LoginRepo {
    suspend fun login(
        email: String,
        password: String,
        notificationToken: String?
    ):Response<UserResponse>

    suspend fun logout(
        company_id:Int?,
        notificationToken: String?
    ):Response<UpdateLeadsRespons>
}