package com.abdullah996.leadscrm.ui.actions

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.actions.ActionRepoImpl

class ActionsViewModel @ViewModelInject constructor(
    private val actionRepoImpl: ActionRepoImpl,
    application: Application
): BaseViewModel(application){
    fun getStatus()= handleFlowResponse {
        actionRepoImpl.getStatus()
    }

    fun updateLeads( lead_id: String?,
                     comment: String?,
                     status_id: String?)= handleFlowResponse {
                         actionRepoImpl.updateStatus(lead_id, comment, status_id)
    }


    fun getAllStatus(id:String)= handleFlowResponse {
        actionRepoImpl.getAllStatus(id)
    }
}