package com.abdullah996.leadscrm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.ActivityHomeBinding
import com.abdullah996.leadscrm.ui.login.LoginViewModel
import com.abdullah996.leadscrm.utill.ApiStatus
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
        binding.home.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.homeSearch.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.menu.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.notification.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.logout.setOnClickListener {
            loginViewModel.logout(null,null).observe(this,{
                when(it.status) {
                    ApiStatus.SUCCESS -> {
                        sharedPreferenceManger.isLoggedIn=false
                        startActivity(Intent(this,MainActivity::class.java))
                        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                    }
                    ApiStatus.ERROR -> {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()

                    }
                    ApiStatus.LOADING -> {

                    }
                }

            })
        }
        binding.createLead.setOnClickListener {

            findNavController(R.id.nav_hot_fragment).navigate(R.id.action_homeFragment2_to_createLeadFragment)
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}