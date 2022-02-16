package com.abdullah996.leadscrm.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentCreateLeadBinding
import com.abdullah996.leadscrm.utill.ApiStatus
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl

class CreateLeadFragment : Fragment() {
    private  var _binding:FragmentCreateLeadBinding?=null
    private val binding get() = _binding!!
    private lateinit var createLeadViewModel: CreateLeadViewModel
    private var name:String?=null
    private var email:String?=null
    private var notes:String?=null
    private  var phonesArr:ArrayList<String> = ArrayList()
    private  var sources:ArrayList<String> = ArrayList()
    private lateinit var sharedPreferenceManger: SharedPreferenceManger





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createLeadViewModel=ViewModelProvider(requireActivity()).get(CreateLeadViewModel::class.java)
        sharedPreferenceManger= SharedPreferenceMangerImpl(requireContext())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.findViewById<ConstraintLayout>(R.id.bottom_nav)?.visibility=View.GONE
        activity?.findViewById<ConstraintLayout>(R.id.top_nav)?.visibility=View.GONE

        _binding = FragmentCreateLeadBinding.inflate(layoutInflater,container,false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.saveEditCreate.setOnClickListener {
            if (checkFields()){
                createLeadViewModel.createLead(sharedPreferenceManger.companyId.toInt(),name,email,notes,phonesArr.toTypedArray(),sources.toTypedArray()).observe(viewLifecycleOwner,{
                    phonesArr=ArrayList()
                    when(it.status) {
                        ApiStatus.SUCCESS -> {
                            findNavController().navigateUp()
                        }
                        ApiStatus.ERROR -> {
                            Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                        }
                        ApiStatus.LOADING -> {

                        }
                    }

                })
            }
        }
    }
    private fun checkFields(): Boolean {

        if(binding.edtNameCreate.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter name", Toast.LENGTH_SHORT).show()
            return false
        } else if(binding.edtMobileCreate.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter mobile", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.edtMobile2Create.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter mobile2", Toast.LENGTH_SHORT).show()
            return false
        } else if(binding.edtEmailEditCreate.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "please enter email", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.editNoteCreate.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "please enter notes", Toast.LENGTH_SHORT).show()
            return false
        }else{
            getFieldsData()
            return true
        }

    }

    private fun getFieldsData() {
        name=binding.edtNameCreate.text.toString()
        email=binding.edtEmailEditCreate.text.toString()
        notes=binding.editNoteCreate.text.toString()
        phonesArr.add(binding.edtMobileCreate.text.toString())
        sources.add(binding.edtMobile2Create.text.toString())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}