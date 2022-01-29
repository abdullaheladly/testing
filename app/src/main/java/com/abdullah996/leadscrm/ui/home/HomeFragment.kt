package com.abdullah996.leadscrm.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.abdullah996.leadscrm.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private val args by navArgs<HomeFragmentArgs>()
    private var token:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        token= activity?.intent?.getStringExtra("token")
      //  Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()

        homeViewModel.getAllLeads(1,"a",null,token!!)
       homeViewModel.leadsResponse.observe(viewLifecycleOwner,{
           Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
       })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}