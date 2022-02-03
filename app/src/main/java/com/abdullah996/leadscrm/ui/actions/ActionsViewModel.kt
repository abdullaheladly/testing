package com.abdullah996.leadscrm.ui.actions

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.model.getstatus.AllStatusReponse
import com.abdullah996.leadscrm.model.updateaction.AddActionResponse
import com.abdullah996.leadscrm.repository.actions.ActionRepoImpl
import com.abdullah996.leadscrm.repository.create.LeadsCreateRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ActionsViewModel @ViewModelInject constructor(
    private val actionRepoImpl: ActionRepoImpl
): BaseViewModel(){
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