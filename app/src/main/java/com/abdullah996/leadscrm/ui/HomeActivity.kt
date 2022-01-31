package com.abdullah996.leadscrm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.ActivityHomeBinding
import com.abdullah996.leadscrm.ui.login.LoginViewModel
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private  var _binding: ActivityHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPreferenceManger: SharedPreferenceManger
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding= ActivityHomeBinding.inflate(layoutInflater)
        sharedPreferenceManger=SharedPreferenceMangerImpl(this)
        binding.logout.setOnClickListener {
            loginViewModel.logout(null,null).observe(this,{
                sharedPreferenceManger.isLoggedIn=false
                startActivity(Intent(this,MainActivity::class.java))
            })
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}