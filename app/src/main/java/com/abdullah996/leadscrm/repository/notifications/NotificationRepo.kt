package com.abdullah996.leadscrm.repository.notifications

import com.abdullah996.leadscrm.model.notifications.NotificationsResponse
import retrofit2.Response

interface NotificationRepo {
    suspend fun getAllNotifications(

    ):Response<NotificationsResponse>
}