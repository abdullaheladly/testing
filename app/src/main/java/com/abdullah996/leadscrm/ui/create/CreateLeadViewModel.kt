package com.abdullah996.leadscrm.ui.create

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.repository.create.LeadsCreateRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CreateLeadViewModel@ViewModelInject constructor(
    private val leadsCreateRepo: LeadsCreateRepoImpl,
    application: Application
): AndroidViewModel(application){
    fun createLead(   company_id: Int?,

                       name: String?,
                       email: String?,
                       notes: String?,
                       phone: Array<String>,
                       sources:Array<String>)= flow<ApiResult<CreateLeadResponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            leadsCreateRepo.createLead(company_id, name, email, notes, phone, sources)
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