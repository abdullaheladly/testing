package com.abdullah996.leadscrm.ui.home

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
    application: Application
): AndroidViewModel(application) {
    var leadsResponse: MutableLiveData<LeedsReponse> = MutableLiveData()


    /*fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String)=viewModelScope.launch {
        leadsResponse.value=leedsRepoImpl.getAllLeads(is_paginate, search, company_id,token).body()
    }*/

    fun getAllLeads(is_paginate: Int, search: String, company_id: Int?,token :String)= flow<ApiResult<LeedsReponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            leedsRepoImpl.getAllLeads(is_paginate, search, company_id, token)
        }
        if (response.isSuccessful){
            emit(ApiResult.Success(response.body()))
        }else{
            /* val errorMsg=response.errorBody()?.toString()
             response.errorBody()?.close()
             emit(ApiResult.Error(errorMsg!!))*/
            if (response.message().toString().contains("timeout")){
                emit(ApiResult.Error("Timeout"))
            }else if (response.code()==401){
                emit(ApiResult.Error("UnAuthenticated"))
            }
            else {
                emit(ApiResult.Error(response.errorBody()?.string().toString()))
                response.errorBody()?.close()
            }
        }
    }.map {
        it
    }.asLiveData()

    fun deleteLead( company_id:Int?,
                     lead_id: String?)= flow<ApiResult<DeleteLeadResponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            deleteLeadRepoImpl.deleteLead(company_id, lead_id)
        }
        if (response.isSuccessful){
            emit(ApiResult.Success(response.body()))
        }else{
            /* val errorMsg=response.errorBody()?.toString()
             response.errorBody()?.close()
             emit(ApiResult.Error(errorMsg!!))*/
            if (response.message().toString().contains("timeout")){
                emit(ApiResult.Error("Timeout"))
            }else if (response.code()==401){
                emit(ApiResult.Error("UnAuthenticated"))
            }
            else {
                emit(ApiResult.Error(response.errorBody()?.string().toString()))
                response.errorBody()?.close()
            }
        }
    }.map {
        it
    }.asLiveData()


}