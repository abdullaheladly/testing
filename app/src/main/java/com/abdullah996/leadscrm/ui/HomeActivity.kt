package com.abdullah996.leadscrm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.ActivityHomeBinding
import com.abdullah996.leadscrm.ui.home.HomeFragment
import com.abdullah996.leadscrm.ui.home.HomeViewModel
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
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchByName:SearchByName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManger= SharedPreferenceMangerImpl(this)
        loginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        homeViewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding= ActivityHomeBinding.inflate(layoutInflater)
        sharedPreferenceManger=SharedPreferenceMangerImpl(this)
        getAllUnreadNotifications()
        binding.home.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.homeSearch.setOnClickListener {
            val fm=supportFragmentManager
            val fragment=fm.findFragmentById(R.id.nav_hot_fragment)
            val f= fragment?.childFragmentManager?.fragments?.get(0) as HomeFragment
            f.onSearchClick()

        }
        binding.menu.setOnClickListener {
            Toast.makeText(this, "This Feature will be added soon ", Toast.LENGTH_SHORT).show()
        }
        binding.notification.setOnClickListener {
            findNavController(R.id.nav_hot_fragment).navigate(R.id.action_homeFragment2_to_notificationsFragment)
        }
        binding.logout.setOnClickListener {
            binding.homePb.visibility=View.VISIBLE
            loginViewModel.logout(sharedPreferenceManger.companyId.toInt(),sharedPreferenceManger.userToken).observe(this,{
                when(it.status) {
                    ApiStatus.SUCCESS -> {
                        binding.homePb.visibility=View.INVISIBLE

                        sharedPreferenceManger.isLoggedIn=false
                        startActivity(Intent(this,MainActivity::class.java))
                        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                    }
                    ApiStatus.ERROR -> {
                        binding.homePb.visibility=View.INVISIBLE
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
     fun getAllUnreadNotifications() : Int {
        var x=0
        homeViewModel.getAllUnreadNotifications().observe(this,{
            when(it.status){
                ApiStatus.SUCCESS->{
                    if (it.data?.data!=null) {
                        if (it.data?.data.isNotEmpty()){
                            x=it.data.data.size
                            binding.unreadNotificationNumber.visibility=View.VISIBLE
                            binding.unreadNotificationNumber.text=it.data.data.size.toString()
                        }else{
                            binding.unreadNotificationNumber.visibility=View.GONE

                        }


                    }else{
                        binding.unreadNotificationNumber.visibility=View.GONE

                    }
                }
                ApiStatus.ERROR->{
                    binding.unreadNotificationNumber.visibility=View.GONE

                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING->{
                    binding.unreadNotificationNumber.visibility=View.GONE

                }
            }
        })
        return x
    }

    interface SearchByName{
        fun onSearchClicked()
    }
}