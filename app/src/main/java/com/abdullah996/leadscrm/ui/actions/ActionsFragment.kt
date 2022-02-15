package com.abdullah996.leadscrm.ui.actions

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentActionsBinding
import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.ui.edit.EditLeadFragmentArgs
import com.abdullah996.leadscrm.utill.ApiStatus
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator


class ActionsFragment : Fragment(),OnActionClickListeners, AdapterView.OnItemSelectedListener {

    private var _binding:FragmentActionsBinding?=null
    private val binding get() = _binding!!
    private val adapter by lazy { ActionsAdapter() }
    private var lead_id:String?=null
    private var comment:String?=null
    private var status:String?=null
    private var status_id:Int?=null
    private val args by navArgs<ActionsFragmentArgs>()
    private var baseAction:BaseAction?=null
    private var list= mutableListOf<String>()
    private lateinit var actionsViewModel: ActionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionsViewModel=ViewModelProvider(requireActivity()).get(ActionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment


        activity?.findViewById<ConstraintLayout>(R.id.bottom_nav)?.visibility=View.GONE
        activity?.findViewById<ConstraintLayout>(R.id.top_nav)?.visibility=View.GONE

        _binding= FragmentActionsBinding.inflate(layoutInflater,container,false)
       lead_id=args.id.toString()
        getStatus()
        getAllStatus()
        setupRecycleView()
        binding.saveEditAction.setOnClickListener {
            uploadStatus()
        }


        return binding.root
    }

    private fun setupRecycleView() {
        binding.rvActions.adapter=adapter
        binding.rvActions.layoutManager= LinearLayoutManager(requireContext())
        binding.rvActions.itemAnimator= SlideInLeftAnimator().apply {
            addDuration=500
        }
    }

    private fun getAllStatus() {
        actionsViewModel.getAllStatus(lead_id!!).observe(viewLifecycleOwner,{
            when(it.status) {
                ApiStatus.SUCCESS -> {
                    adapter.saveData(it.data!!.data)
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        })
    }

    private fun uploadStatus() {
            comment=binding.actionsCommentsTxt.text.toString()
            status=binding.actionStatusSpinner.selectedItem.toString()
            for (item in baseAction?.data!!){
                if (status==item.name){
                    status_id=item.id
                }
            }
            if (status_id==null){
                status_id=1
            }

            actionsViewModel.updateLeads(lead_id!!,comment,status_id!!.toString()).observe(viewLifecycleOwner, {
                when (it.status) {
                    ApiStatus.SUCCESS -> {
                        getAllStatus()
                        binding.actionsCommentsTxt.setText("")
                    }
                    ApiStatus.ERROR -> {
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                            .show()

                    }
                    ApiStatus.LOADING -> {

                    }
                }
            })

    }

    private fun getStatus() {
        actionsViewModel.getStatus().observe(viewLifecycleOwner,{
            when(it.status) {
                ApiStatus.SUCCESS -> {
                    baseAction=it.data
                    for (item in it.data!!.data){
                        list.add(item.name)
                    }
                    setSpinner()
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        })
    }

    private fun setSpinner() {
        ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,list.toTypedArray()).also {
            it.setDropDownViewResource(android.R.layout.simple_list_item_1)
            binding.actionStatusSpinner.adapter=it
        }


        binding.actionStatusSpinner.onItemSelectedListener=this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onSaveActionClick(id: Int) {
        TODO("Not yet implemented")
    }


}