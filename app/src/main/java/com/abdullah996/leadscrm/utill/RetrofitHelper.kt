package com.abdullah996.leadscrm.utill


import android.util.Log
import com.abdullah996.leadscrm.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


private fun responseErrorHandler(response: String, responsecode: Int): String {

    return when {
        responsecode < 500 -> {
//            if (responsecode == 401) {
//                SharedPreferencesManagerImpl(LeedoApplication.appContext).isLoggedIn = false
//
//                val i = Intent(context, AuthActivity::class.java)
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(i)
//            }
            try {
                val responseObject = JSONObject(response)
//                if (responseObject.has("data")) {
//                    return responseObject.getString("data")
//                }
//                if (responseObject.has("errors")) {
//                    val dataObject = responseObject.getJSONObject("errors")
//                    if (dataObject.has("email"))
//                        return dataObject.getJSONArray("email").getString(0)
//                }
                if (responseObject.has("errors")) {
                    val error = responseObject.get("errors")
                    if (error is String)
                        return error
                    if (error is JSONObject) {
                        for (key: String in error.keys().iterator()) {
                            return error.getString(key)
                        }
                    }
                }
                responseObject.getString("message")
            } catch (e: Exception) {
                Log.d("error", "responseErrorHandler: ")
                if (null != e.message)
                    e.message!!
                else
                    "error"

            }
        }
        responsecode == 500 -> {
            "server error"
        }
        else -> {
            "error"
        }
    }
}

private fun failureHandler(t: Throwable): String {

    return if (t is IOException) {
        "No internet"
    } else {

       "Error"
    }
}

fun errorHandler(throwable: Throwable): String? {

    try {
        return if (throwable is HttpException) {
            val msg = responseErrorHandler(
                throwable.response()!!.errorBody()!!.string(),
                throwable.code()
            )
            msg.replace("[", "").replace("]", "")
        } else failureHandler(throwable)
    } catch (e: Exception) {

    }
    return null

}