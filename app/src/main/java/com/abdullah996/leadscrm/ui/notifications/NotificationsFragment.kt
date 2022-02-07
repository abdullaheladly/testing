package com.abdullah996.leadscrm.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentNotificationsBinding
import com.abdullah996.leadscrm.ui.actions.ActionsAdapter
import com.abdullah996.leadscrm.utill.ApiStatus
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator


class NotificationsFragment : Fragment() {

    private var _binding:FragmentNotificationsBinding?=null
    private val binding get() = _binding!!
    private val adapter by lazy { NotificationsAdapter() }

    private lateinit var notificationsViewModel: NotificationsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationsViewModel=ViewModelProvider(requireActivity()).get(NotificationsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentNotificationsBinding.inflate(layoutInflater,container,false)
        notificationsViewModel.getAllNotifications().observe(viewLifecycleOwner,{
            when(it.status) {
                ApiStatus.SUCCESS -> {
                    adapter.saveData(it.data?.data?.data!!)
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        })
        setupRecycleView()
        return binding.root
    }

    private fun setupRecycleView() {
        binding.rvNotifications.adapter=adapter
        binding.rvNotifications.layoutManager= LinearLayoutManager(requireContext())
        binding.rvNotifications.itemAnimator= SlideInLeftAnimator().apply {
            addDuration=500
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}