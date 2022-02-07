package com.abdullah996.leadscrm.ui.home

import android.app.Activity
import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.delete.DeleteLeadResponse
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.repository.LeedsRepoImpl
import com.abdullah996.leadscrm.repository.LoginRepoImpl
import com.abdullah996.leadscrm.repository.delete.DeleteLeadRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(
    private val leedsRepoImpl: LeedsRepoImpl,
    private val deleteLeadRepoImpl: DeleteLeadRepoImpl,
    application: Application

): BaseViewModel(application) {


    fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String)= handleFlowResponse {
        leedsRepoImpl.getAllLeads(is_paginate, search, company_id, token)
    }

    fun deleteLead( company_id:Int?,
                     lead_id: String?)= handleFlowResponse {
                         deleteLeadRepoImpl.deleteLead(company_id,lead_id)
    }


    fun filterByData(  year:String,
                       month: Array<String>)=handleFlowResponse {
                           leedsRepoImpl.filterByDate(year, month)
    }


}