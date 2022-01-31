package com.abdullah996.leadscrm.ui.login

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.repository.LoginRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response


class LoginViewModel @ViewModelInject constructor(
private val loginRepoImpl: LoginRepoImpl,
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
application: Application
):AndroidViewModel(application) {
    var loginResponse: MutableLiveData<UserResponse> = MutableLiveData()



   /* fun login(email:String,password:String,fcm_token:String?) = handleRequestLiveData<UserResponse> {

        val result= withContext(Dispatchers.IO){
            loginRepoImpl.login(email,password,fcm_token)
        }
        result.let {
            //add user to shared preferences

        }
        emit(result)


     }*/
   fun logout( company_id: Int?,
               notificationToken: String?)= flow<ApiResult<UpdateLeadsRespons>> {
       emit(ApiResult.Loading(null,true))
       val response= withContext(Dispatchers.IO){
           loginRepoImpl.logout(company_id,notificationToken)
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


    fun userLiveData(email:String,password:String,fcm_token:String?)= flow<ApiResult<UserResponse>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            loginRepoImpl.login(email,password,fcm_token)
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