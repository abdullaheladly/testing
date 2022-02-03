package com.abdullah996.leadscrm.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentEditLeadBinding
import com.abdullah996.leadscrm.utill.ApiStatus

class EditLeadFragment : Fragment() {
    private var _binding:FragmentEditLeadBinding?=null
    private val binding get() = _binding!!
    private lateinit var editLeadViewModel:EditLeadViewModel
    private val args by navArgs<EditLeadFragmentArgs>()
    private  var name:String?=null
    private  var mobile:String?=null
    private  var mobile2:String?=null
    private  var email:String?=null
    private  var note:String?=null
    private  var reasons:String?=null
    private  var isQualified:String="0"



    private var phones= mutableListOf<String>()

    private  var phonesArr:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editLeadViewModel= ViewModelProvider(requireActivity()).get(EditLeadViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        activity?.findViewById<ConstraintLayout>(R.id.bottom_nav)?.visibility=View.GONE

        _binding= FragmentEditLeadBinding.inflate(layoutInflater,container,false)
        setViews()

        binding.saveEdit.setOnClickListener {
            if (checkFields()){

                editLeadViewModel.getAllLeads(null,args.lead.id.toString(),name,email,note,
                   phonesArr.toTypedArray() ,isQualified,reasons!!).observe(viewLifecycleOwner,{
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

        return binding.root
    }



    private fun setViews() {
        binding.edtName.setText(args.lead.name.toString())
        binding.edtMobile.setText(args.lead.phones[0].toString())
        if (args.lead.phones.size>1){
            binding.edtMobile2.setText(args.lead.phones[1].toString())
        }
        binding.edtEmailEdit.setText(args.lead.email.toString())
        binding.editNote.setText(args.lead.notes.toString())
    }


    private fun checkFields(): Boolean {

        if ( binding.edtUnitInterestEdit.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter unit interest", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.edtName.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter name", Toast.LENGTH_SHORT).show()
            return false
        } else if(binding.edtMobile.text.isNullOrEmpty()){
        Toast.makeText(requireContext(), "please enter mobile", Toast.LENGTH_SHORT).show()
        return false
        }/*else if(binding.edtMobile2.text.isNullOrEmpty()){
            Toast.makeText(requireContext(), "please enter mobile2", Toast.LENGTH_SHORT).show()
            return false
        }*/ else if(binding.edtEmailEdit.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "please enter email", Toast.LENGTH_SHORT).show()
            return false
        }else if(binding.editNote.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "please enter notes", Toast.LENGTH_SHORT).show()
            return false
        }
       else{
            reasons=binding.edtUnitInterestEdit.text.toString()
            name=binding.edtName.text.toString()
            mobile=binding.edtMobile.text.toString()
            phonesArr.add(mobile!!)
            Toast.makeText(requireContext(), phonesArr.toList().toString(), Toast.LENGTH_SHORT).show()
            if (binding.edtMobile2.text.isNullOrEmpty()){

            }else{
                mobile2=binding.edtMobile2.text.toString()
                phonesArr.add(mobile2!!)
            }

            email=binding.edtEmailEdit.text.toString()
            note=binding.editNote.text.toString()
            if (binding.edtIsQualifiedSpinner.isChecked){
                isQualified="1"
            }else{
                isQualified="0"
            }


            return true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }




}