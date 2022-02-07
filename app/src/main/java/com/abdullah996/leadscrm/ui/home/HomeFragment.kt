package com.abdullah996.leadscrm.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.abdullah996.leadscrm.databinding.FragmentHomeBinding
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.ui.HomeActivity
import com.abdullah996.leadscrm.utill.ApiStatus
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment(),OnLeadsClickListener, AdapterView.OnItemSelectedListener  {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private val args by navArgs<HomeFragmentArgs>()
    private var token:String?=null
    private val leadsAdapter by lazy { LeadsAdapter(this) }
    private lateinit var sharedPreferenceManger: SharedPreferenceManger




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        sharedPreferenceManger=SharedPreferenceMangerImpl(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        token= activity?.intent?.getStringExtra("token")
        setSpinners()
      // Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()

        /*binding.txtNewLeads.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_createLeadFragment)
        }*/
        setupSwipeTorefresh()
        setupRecycleView()
        if (hasInternetConnection()){
            binding.sToRefresh.isRefreshing=true
            getAllLeads()
        }else{
            Toast.makeText(requireContext(), "no internet ", Toast.LENGTH_SHORT).show()
        }


        setListeners()


       /* homeViewModel.getAllLeads(1,"a",null,sharedPreferenceManger.userToken)
       homeViewModel.leadsResponse.observe(viewLifecycleOwner,{
           Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
           leadsAdapter.saveData(it.data.data)
       })*/
        return binding.root
    }

    private fun setListeners() {
        binding.goFilter.setOnClickListener {
            binding.sToRefresh.isRefreshing=true
            val year=binding.yearSpinner.selectedItem.toString()
            val month=binding.monthSpinner.selectedItem.toString()
            val months= arrayOf(month)
            homeViewModel.filterByData(year,months).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {
                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE

                        }else{
                            binding.rvLeads.visibility=View.INVISIBLE
                            binding.noDataFound.visibility=View.VISIBLE
                            binding.sToRefresh.isRefreshing=false


                        }
                    }
                    ApiStatus.ERROR->{
                        makeToast(it.message.toString())
                        binding.sToRefresh.isRefreshing=false
                    }
                    ApiStatus.LOADING->{

                    }
                }
            })
        }
    }

    @SuppressLint("ResourceType")
    private fun setSpinners() {
        ArrayAdapter.createFromResource(requireContext(),R.array.years,android.R.layout.simple_list_item_1).also {
            binding.yearSpinner.adapter=it
        }
        binding.yearSpinner.onItemSelectedListener=this
        ArrayAdapter.createFromResource(requireContext(),R.array.months,android.R.layout.simple_list_item_1).also {
            binding.monthSpinner.adapter=it
        }
        binding.monthSpinner.onItemSelectedListener=this
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<ConstraintLayout>(R.id.bottom_nav)?.visibility=View.VISIBLE
        activity?.findViewById<ConstraintLayout>(R.id.top_nav)?.visibility=View.VISIBLE


    }

    private fun setupSwipeTorefresh() {
        binding.sToRefresh.setOnRefreshListener {
            binding.sToRefresh.isRefreshing =true
            if (hasInternetConnection()){
                getAllLeads()
            }
            else{
                makeToast("no internet")
                binding.sToRefresh.isRefreshing=false
            }

        }
    }

    fun getAllLeads(){
        homeViewModel.getAllLeads(1,"a",null,sharedPreferenceManger.userToken).observe(viewLifecycleOwner,{
            when(it.status){
                ApiStatus.SUCCESS->{
                    if (it.data?.data?.data!=null) {
                        leadsAdapter.saveData(it.data.data.data)
                        binding.sToRefresh.isRefreshing=false
                        binding.rvLeads.visibility=View.VISIBLE

                        binding.noDataFound.visibility=View.GONE
                    }
                }
                ApiStatus.ERROR->{
                    makeToast(it.message.toString())
                    binding.sToRefresh.isRefreshing=false
                }
                ApiStatus.LOADING->{

                }
            }
        })
    }
    fun makeToast(s:String){
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecycleView() {
        binding.rvLeads.adapter=leadsAdapter
        binding.rvLeads.layoutManager=LinearLayoutManager(requireContext())
        binding.rvLeads.itemAnimator=SlideInLeftAnimator().apply {
            addDuration=500
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



    override fun onEditInfoClick(lead: Leads) {
        val action=HomeFragmentDirections.actionHomeFragment2ToEditLeadFragment2(lead)
        findNavController().navigate(action)
    }

    override fun onAddActionClick(id: Int) {
        val action=HomeFragmentDirections.actionHomeFragment2ToActionsFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteLeadClick(id: Int) {
        if (hasInternetConnection()){
        homeViewModel.deleteLead(null,id.toString()).observe(viewLifecycleOwner,{
            when(it.status){
                ApiStatus.SUCCESS->{
                    binding.sToRefresh.isRefreshing=true
                    getAllLeads()
                }
                ApiStatus.ERROR->{
                    makeToast(it.message.toString())
                    binding.sToRefresh.isRefreshing=false
                }
                ApiStatus.LOADING->{

                }

        }

    })
        }
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager=requireActivity().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork=connectivityManager.activeNetwork?:return false
        val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return  when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}