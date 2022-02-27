package com.abdullah996.leadscrm.repository.notifications

import com.abdullah996.leadscrm.model.notifications.NotificationsResponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.network.Apis
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject


@ActivityRetainedScoped
class NotificationsRepoImpl @Inject constructor(
    private val apis: Apis
) :NotificationRepo {
    override suspend fun getAllNotifications(): Response<NotificationsResponse> {
        return apis.getAllNotifications()
    }

    override suspend fun markAsRead(): Response<UpdateLeadsRespons> {
        return apis.markAllAsRead()
    }
}