package com.abdullah996.leadscrm.ui.create

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.repository.create.LeadsCreateRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CreateLeadViewModel@ViewModelInject constructor(
    private val leadsCreateRepo: LeadsCreateRepoImpl
): BaseViewModel(){
    fun createLead(   company_id: Int?,
                       name: String?,
                       email: String?,
                       notes: String?,
                       phone: Array<String>,
                       sources:Array<String>)=handleFlowResponse {
                           leadsCreateRepo.createLead(company_id, name, email, notes, phone, sources)
    }
}