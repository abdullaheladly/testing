package com.abdullah996.leadscrm.ui.login

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.repository.login.LoginRepoImpl


class LoginViewModel @ViewModelInject constructor(
    private val loginRepoImpl: LoginRepoImpl,
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
//application: Application
    application: Application

):BaseViewModel(application)
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