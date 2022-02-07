package com.abdullah996.leadscrm.utill

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

interface SharedPreferenceManger{
    var userToken: String
    var isLoggedIn:Boolean
    var companyId:String

}
class SharedPreferenceMangerImpl(context: Context):SharedPreferenceManger{
private val sharedPreference:SharedPreferences=
    context.getSharedPreferences(
        sharedPreferencesKey,Context.MODE_PRIVATE
    )
    private val editor=sharedPreference.edit()


    override var userToken: String
        get() = getString(userTokenKey)
        set(value) {
            editor.putString(userTokenKey,value).apply()
        }
    override var isLoggedIn: Boolean
        get() = sharedPreference.getBoolean(isLoggedInKey,false)
        set(value) {
            editor.putBoolean(isLoggedInKey,value).apply()
        }
    override var companyId: String
        get() = getString(companyIDKEY)
        set(value) {editor.putString(companyIDKEY,value).apply()}


    private fun getString(key: String): String {
        sharedPreference.getString(key, "").let { s ->
            return if (s.isNullOrBlank())
                ""
            else
                s
        }
    }

    private fun getInt(key: String): Int {
        sharedPreference.getInt(key, 0).let { s ->
            return if (s == null)
                0
            else
                s
        }
    }

    companion object {
        private const val sharedPreferencesKey = "USERDATA"

        private const val userTokenKey = "API_TOKEN"
        private const val userIDKey = "USERID"
        private const val userNameKey = "USERNAME"
        private const val userPhoneKey = "USERPHONE"
        private const val userEmailKey = "USEREMAIL"
        private const val userImageKey = "USERIMAGE"
        private const val userDataKey = "USERDATA"
        private const val userSlugKey = "USERSlug"
        private const val choseLanguageKey = "CHOSELANGUAGE"
        private const val termsAgreedKey = "TERMSAGREED"
        private const val FirstTimeKey = "TERMSAGREED"
        private const val notificationTokenKey = "NotificationToken"

        private const val selectedProgramIDKEY = "SelectedProgramID"
        private const val companyIDKEY = "CompanyID"


        private const val isLoggedInKey = "LOGIN"
    }

}