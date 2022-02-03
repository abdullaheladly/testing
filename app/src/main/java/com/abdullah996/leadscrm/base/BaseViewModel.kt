package com.abdullah996.leadscrm.base


import androidx.lifecycle.*
import com.abdullah996.leadscrm.utill.ApiResult
import com.abdullah996.leadscrm.utill.errorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    fun <T> handleFlowResponse(requst:suspend () -> Response<T>)= flow<ApiResult<T>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            requst.invoke()
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
            } else if (response.code()<500){
                val responseObject= JSONObject(response.errorBody()?.string().toString())

                if (responseObject.has("errors")){
                    val error=responseObject.get("errors")
                    if (error is String){
                        emit(ApiResult.Error(error))
                    }
                    if (error is JSONObject){
                        for (key:String in error.keys().iterator()){
                            emit(ApiResult.Error(error.getString(key)))
                        }

                    }
                }
                emit(ApiResult.Error(responseObject.getString("message")))


                // emit(ApiResult.Error(response.errorBody()?.string().toString()))
                response.errorBody()?.close()
            }
            else {
                emit(ApiResult.Error("error"))

            }
        }
    }.map {
        it
    }.asLiveData()



}