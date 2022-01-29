package com.abdullah996.leadscrm.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdullah996.leadscrm.R

import com.abdullah996.leadscrm.databinding.FragmentLoginBinding
import com.abdullah996.leadscrm.ui.HomeActivity
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject
import kotlin.math.log


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var loginViewModel:LoginViewModel
    private lateinit var email:String
    private lateinit var password:String
    private  var token:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel= ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentLoginBinding.inflate(layoutInflater,container,false)
        getNotificationToken()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            if (checkValidInput()){
                    loginViewModel.login(email, password, token?:null).observe(viewLifecycleOwner,{
                        makeToast(it.data!!.token!!)
                        /*val action=LoginFragmentDirections.actionLoginFragmentToHomeFragment(it.data!!.token)
                        findNavController().navigate(action)*/
                        val intet=Intent(requireActivity(),HomeActivity::class.java)
                        intet.putExtra("token",it.data.token)
                        startActivity(intet)
                    })
                   /* loginViewModel.loginResponse.observe(viewLifecycleOwner, {

                    })*/
            }
        }

    }
    private fun getNotificationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful){

                token=it.result
                Log.d("ahmed",it.result)
            }else{
                makeToast("not su ")
            }
        }
    }

    private fun checkValidInput():Boolean {
        if (binding.edtEmail.text.isNullOrEmpty()){
            makeToast("Email should  not be Null")
            return false
        }
        else if (binding.edtPassword.text.isNullOrEmpty()){
            makeToast("Password should not be Null")
            return false
        }
        else{
            email=edt_email.text.toString()
            password=edt_password.text.toString()
            return true
        }

    }

    private fun makeToast(s:String){
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}