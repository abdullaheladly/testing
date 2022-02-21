package com.abdullah996.leadscrm.ui.notifications

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.notifications.NotificationsRepoImpl

class NotificationsViewModel @ViewModelInject constructor(
    private val notificationRepo: NotificationsRepoImpl,
    application: Application

) :BaseViewModel(application) {
    fun getAllNotifications()=handleFlowResponse {
        notificationRepo.getAllNotifications()
    }
}