package com.abdullah996.leadscrm.base


import androidx.lifecycle.*
import com.abdullah996.leadscrm.utill.ApiResult
import com.abdullah996.leadscrm.utill.errorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    val showLoading = MutableLiveData<Boolean>()
    val showNoData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }


    fun <T> callRequestWaitLiveData(request: suspend () -> T) = liveData(Dispatchers.Main) {
        try {
            showLoading.postValue(true)
            val result = withContext(Dispatchers.IO) {
                request()
            }

            showLoading.postValue(false)

            emit(result)

        } catch (exception: Exception) {
            showLoading.postValue(false)
            errorLiveData.postValue(errorHandler(exception))
        }
    }


    fun <T> callRequestLiveData(request: suspend () -> T) = liveData(Dispatchers.Main) {
        try {
            showLoading.postValue(true)
            val result = withContext(Dispatchers.IO) {
                request()
            }
            emit(result)

            showLoading.postValue(false)

        } catch (exception: Exception) {
            showLoading.postValue(false)
            errorLiveData.postValue(errorHandler(exception))
        }
    }

    fun <T> handleRequestLiveData(block: suspend LiveDataScope<T>.() -> Unit) =
        liveData(Dispatchers.Main) {
            try {
                showLoading.postValue(true)
                block()
                showLoading.postValue(false)

            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(errorHandler(exception))
            }
        }

    fun <T> fullHandelRequestLiveData(block: suspend LiveDataScope<T>.() -> Unit) =
        liveData(Dispatchers.Main) {
            try {
                block()
            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(errorHandler(exception))
            }
        }

    fun <T> callRequest(request: suspend () -> T, liveData: MutableLiveData<T>) {
        launch {
            try {
                showLoading.postValue(true)

                val result = withContext(Dispatchers.IO) {
                    request()
                }

                liveData.postValue(result)

                showLoading.postValue(false)
            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(
                    errorHandler(
                        exception
                    )
                )
            }
        }
    }

    fun <T> callRequestWait(request: suspend () -> T, liveData: MutableLiveData<T>) {
        launch {
            try {
                showLoading.postValue(true)

                val result = withContext(Dispatchers.IO) {
                    request()
                }

                showLoading.postValue(false)

                liveData.postValue(result)
            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(
                    errorHandler(
                        exception
                    )
                )
            }
        }
    }

   /* fun <T> handleFlowResponse(requst:suspend () -> Response<T>)= flow<ApiResult<T>> {
        emit(ApiResult.Loading(null,true))
        val response= withContext(Dispatchers.IO){
            requst
        }
        if (response.){
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
    }.asLiveData()*/



    fun handleRequest(block:suspend CoroutineScope.() -> Unit
    ) {
        launch {
            try {
                showLoading.postValue(true)

                block()

                showLoading.postValue(false)

            } catch (exception: Exception) {
                showLoading.postValue(false)
                errorLiveData.postValue(
                    errorHandler(
                        exception
                    )
                )
            }
        }
    }
}