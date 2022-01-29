package com.abdullah996.leadscrm.base


import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.abdullah996.leadscrm.utill.errorHandler
import kotlinx.coroutines.*
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