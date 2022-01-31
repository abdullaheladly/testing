package com.abdullah996.leadscrm.ui.edit

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.model.leeds.LeedsReponse
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.repository.UpdateLeadImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EditLeadViewModel @ViewModelInject constructor(
    private val updateLeadImpl: UpdateLeadImpl,
    application: Application
):AndroidViewModel(application) {
    fun getAllLeads(  company_id:Int?,
                      lead_id: String?,
                      name:String?,
                      email: String?,
                      notes:String?,
                      phone:Array<String>,
                      is_qualified :String,
                      reasons :String,)= flow<ApiResult<UpdateLeadsRespons>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            updateLeadImpl.updateLead(company_id, lead_id, name, email, notes,phone, is_qualified, reasons)
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