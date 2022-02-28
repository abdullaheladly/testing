package com.abdullah996.leadscrm.ui.edit

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.updatelead.UpdateLeadImpl

class EditLeadViewModel @ViewModelInject constructor(
    private val updateLeadImpl: UpdateLeadImpl,
    application: Application
):BaseViewModel(application) {
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