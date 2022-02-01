package com.abdullah996.leadscrm.ui.actions

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.create.CreateLeadResponse
import com.abdullah996.leadscrm.model.getstatus.AllStatusReponse
import com.abdullah996.leadscrm.model.updateaction.AddActionResponse
import com.abdullah996.leadscrm.repository.actions.ActionRepoImpl
import com.abdullah996.leadscrm.repository.create.LeadsCreateRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ActionsViewModel @ViewModelInject constructor(
    private val actionRepoImpl: ActionRepoImpl,
    application: Application
): AndroidViewModel(application){
    fun getStatus()= flow<ApiResult<BaseAction>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            actionRepoImpl.getStatus()
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

    fun updateLeads( lead_id: String?,
                     comment: String?,
                     status_id: String?)= flow<ApiResult<AddActionResponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            actionRepoImpl.updateStatus(lead_id, comment, status_id)
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


    fun getAllStatus(id:String)= flow<ApiResult<AllStatusReponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            actionRepoImpl.getAllStatus(id)
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