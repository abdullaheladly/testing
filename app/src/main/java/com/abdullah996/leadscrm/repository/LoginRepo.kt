package com.abdullah996.leadscrm.repository

import com.abdullah996.leadscrm.model.user.UserResponse
import retrofit2.Response

interface LoginRepo {
    suspend fun login(
        email: String,
        password: String,
        notificationToken: String?
    ):Response<UserResponse>
}