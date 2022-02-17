package com.abdullah996.leadscrm.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentHomeBinding
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.utill.ApiStatus
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator



@AndroidEntryPoint
class HomeFragment : Fragment(),OnLeadsClickListener, AdapterView.OnItemSelectedListener  {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
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
    ): View {

        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        token= activity?.intent?.getStringExtra("token")
        setSpinners()
      // Toast.makeText(requireContext(), token, Toast.LENGTH_SHORT).show()

        /*binding.txtNewLeads.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_createLeadFragment)
        }*/
        setupSwipeRefresh()
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
        binding.btnClearFilter.setOnClickListener {
            binding.filterLayout.visibility=View.GONE
            binding.sToRefresh.isRefreshing=true
            getAllLeads()
            binding.filterBySpinner.setSelection(0)
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

        ArrayAdapter.createFromResource(requireContext(),R.array.filters,android.R.layout.simple_list_item_1).also {
            binding.filterBySpinner.adapter=it
        }
        binding.filterBySpinner.onItemSelectedListener=this
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<ConstraintLayout>(R.id.bottom_nav)?.visibility=View.VISIBLE
        activity?.findViewById<ConstraintLayout>(R.id.top_nav)?.visibility=View.VISIBLE


    }

    private fun setupSwipeRefresh() {
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

    private fun getAllLeads(){
        try {
        homeViewModel.getAllLeads(1,"a",sharedPreferenceManger.companyId.toInt(),sharedPreferenceManger.userToken).observe(viewLifecycleOwner,{
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
    }catch (ex:Exception){
        makeToast("something went wrong ")

        }
    }
   private fun makeToast(s:String){
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
        homeViewModel.deleteLead(sharedPreferenceManger.companyId.toInt(),id.toString()).observe(viewLifecycleOwner,{
            when(it.status){
                ApiStatus.SUCCESS->{
                    binding.sToRefresh.isRefreshing=true
                    getAllLeads()
                }
                ApiStatus.ERROR->{
                    makeToast(it.message.toString())
                    binding.sToRefresh.isRefreshing=false
                    if (it.message.toString()=="UnAuthenticated"){
                        sharedPreferenceManger.isLoggedIn=false
                    }

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
        if (binding.filterBySpinner.selectedItem=="Date"){
            binding.filterLayout.visibility=View.VISIBLE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
    fun onSearchClick() {
        var text: String
        val mDialog= LayoutInflater.from(requireContext()).inflate(R.layout.search_card_dialog,null)
        val mBuilder=AlertDialog.Builder(requireContext())
            .setView(mDialog)
          //  .setTitle("Search For Lead By Name")
        val  mAlertDialog = mBuilder.show()
        val button=mDialog.findViewById<Button>(R.id.search_by_name_btn)
        val editText=mDialog.findViewById<EditText>(R.id.search_by_name_edt)
        button.setOnClickListener {
            mAlertDialog.dismiss()
            text=editText.text.toString()
            binding.sToRefresh.isRefreshing=true
            homeViewModel.searchBuName(1,text,sharedPreferenceManger.companyId.toInt(),sharedPreferenceManger.userToken).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {
                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE
                        }else{
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.INVISIBLE
                            binding.noDataFound.visibility=View.VISIBLE
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
}