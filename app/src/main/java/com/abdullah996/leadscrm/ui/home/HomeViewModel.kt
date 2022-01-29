package com.abdullah996.leadscrm.ui.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.repository.LeedsRepoImpl
import com.abdullah996.leadscrm.repository.LoginRepoImpl
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val leedsRepoImpl: LeedsRepoImpl,
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
    application: Application
): AndroidViewModel(application) {
    var leadsResponse: MutableLiveData<LeedsReponse> = MutableLiveData()


    fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String)=viewModelScope.launch {
        leadsResponse.value=leedsRepoImpl.getAllLeads(is_paginate, search, company_id,token)
    }


}