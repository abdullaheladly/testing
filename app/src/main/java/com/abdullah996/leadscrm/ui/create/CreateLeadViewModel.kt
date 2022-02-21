package com.abdullah996.leadscrm.ui.create

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.create.LeadsCreateRepoImpl

class CreateLeadViewModel@ViewModelInject constructor(
    private val leadsCreateRepo: LeadsCreateRepoImpl,
    application: Application
): BaseViewModel(application){
    fun createLead(   company_id: Int?,
                       name: String?,
                       email: String?,
                       notes: String?,
                       phone: Array<String>,
                       sources:Array<String>)=handleFlowResponse {
                           leadsCreateRepo.createLead(company_id, name, email, notes, phone, sources)
    }
}