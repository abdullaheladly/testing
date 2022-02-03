package com.abdullah996.leadscrm.ui.login

import android.app.Application
import androidx.datastore.preferences.protobuf.Api
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.updateleads.UpdateLeadsRespons
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.repository.LoginRepoImpl
import com.abdullah996.leadscrm.utill.ApiResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.Response


class LoginViewModel @ViewModelInject constructor(
private val loginRepoImpl: LoginRepoImpl,
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
//application: Application
):BaseViewModel()
//AndroidViewModel(application)
 {

   fun logout( company_id: Int?,
               notificationToken: String?)= handleFlowResponse {
                   loginRepoImpl.logout(company_id, notificationToken)
   }


    fun userLiveData(email:String,password:String,fcm_token:String?)=handleFlowResponse {
        loginRepoImpl.login(email,password,fcm_token)
    }


}