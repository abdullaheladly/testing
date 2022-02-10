package com.abdullah996.leadscrm.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.ActivitySplashBinding
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var _binding:ActivitySplashBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPreferenceManger: SharedPreferenceManger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        _binding= ActivitySplashBinding.inflate(layoutInflater)
        binding.logoSplash.startAnimation(AnimationUtils.loadAnimation(this,R.anim.pulse))
        GlobalScope.launch(Dispatchers.Main){
            goToLogin()
        }
        setContentView(binding.root)
    }
    private suspend fun goToLogin(){
        delay(2000)
        sharedPreferenceManger= SharedPreferenceMangerImpl(this)
        if (sharedPreferenceManger.isLoggedIn){
            val intet= Intent(this,HomeActivity::class.java)
            startActivity(intet)

        }else{
            val intet= Intent(this,MainActivity::class.java)
            startActivity(intet)
        }
    }
}