package com.abdullah996.leadscrm.ui.edit

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.repository.UpdateLeadImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EditLeadViewModel @ViewModelInject constructor(
    private val updateLeadImpl: UpdateLeadImpl
):BaseViewModel() {
    fun getAllLeads(  company_id:Int?,
                      lead_id: String?,
                      name:String?,
                      email: String?,
                      notes:String?,
                      phone:Array<String>,
                      is_qualified :String,
                      reasons :String,
    )= handleFlowResponse {
        updateLeadImpl.updateLead(company_id, lead_id, name, email, notes, phone, is_qualified, reasons)
    }
}