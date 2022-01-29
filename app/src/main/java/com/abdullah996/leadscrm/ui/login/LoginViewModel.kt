package com.abdullah996.leadscrm.ui.login

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdullah996.leadscrm.base.BaseViewModel
import com.abdullah996.leadscrm.model.user.UserResponse
import com.abdullah996.leadscrm.repository.LoginRepoImpl
import com.abdullah996.leadscrm.utill.NetworkResult
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import retrofit2.Response


class LoginViewModel @ViewModelInject constructor(
private val loginRepoImpl: LoginRepoImpl,
//private val sharedPreferenceMangerImpl: SharedPreferenceMangerImpl,
//application: Application
):BaseViewModel() {
    var loginResponse: MutableLiveData<UserResponse> = MutableLiveData()



    fun login(email:String,password:String,fcm_token:String?) = handleRequestLiveData<UserResponse> {

        val result= withContext(Dispatchers.IO){
            loginRepoImpl.login(email,password,fcm_token)
        }
        result.let {
            //add user to shared preferences

        }
        emit(result)


     }


}