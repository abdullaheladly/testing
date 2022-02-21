package com.abdullah996.leadscrm.ui.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.LeedsRepoImpl
import com.abdullah996.leadscrm.repository.delete.DeleteLeadRepoImpl
import com.abdullah996.leadscrm.repository.filter.LeadsFilterImpl


class HomeViewModel @ViewModelInject constructor(
    private val leedsRepoImpl: LeedsRepoImpl,
    private val deleteLeadRepoImpl: DeleteLeadRepoImpl,
    private val leadsFilterImpl: LeadsFilterImpl,
    application: Application

): BaseViewModel(application) {


    fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String)= handleFlowResponse {
        leedsRepoImpl.getAllLeads(is_paginate, search, company_id, token)
    }
    fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String,page:Int)= handleFlowResponse {
        leedsRepoImpl.getAllLeads(is_paginate, search, company_id, token,page)
    }

    fun searchBuName(is_paginate: Int, search: String, company_id: Int?,token :String)= handleFlowResponse {
        leedsRepoImpl.searchByName(is_paginate, search, company_id, token)
    }

    fun deleteLead( company_id:Int?, lead_id: String?)= handleFlowResponse {
                         deleteLeadRepoImpl.deleteLead(company_id,lead_id)
    }


    fun filterByData(  year:String, month: Array<String>)=handleFlowResponse {
                           leedsRepoImpl.filterByDate(year, month)
    }

    fun filterByTag (tag: String) = handleFlowResponse {
        leadsFilterImpl.filterByTag(tag)
    }


    fun filterByType(type:String)=handleFlowResponse {
        leadsFilterImpl.filterByType(type)
    }




}