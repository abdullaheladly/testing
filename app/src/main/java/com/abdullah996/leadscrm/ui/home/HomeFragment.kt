package com.abdullah996.leadscrm.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.databinding.FragmentHomeBinding
import com.abdullah996.leadscrm.model.baseactions.BaseAction
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.model.statusmodel.StatusModelResponse
import com.abdullah996.leadscrm.ui.SplashActivity
import com.abdullah996.leadscrm.ui.actions.ActionsViewModel
import com.abdullah996.leadscrm.utill.ApiStatus
import com.abdullah996.leadscrm.utill.SharedPreferenceManger
import com.abdullah996.leadscrm.utill.SharedPreferenceMangerImpl
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : Fragment(),OnLeadsClickListener, AdapterView.OnItemSelectedListener  {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var actionsViewModel: ActionsViewModel
    private var token:String?=null
    private val leadsAdapter by lazy { LeadsAdapter(this) }
    private lateinit var sharedPreferenceManger: SharedPreferenceManger
    private var pageNumber=1;
    private var nostatus=0;
    private var statusId=0;
    private var baseAction:StatusModelResponse?=null
    private var list= mutableListOf<String>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel= ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        actionsViewModel= ViewModelProvider(requireActivity()).get(ActionsViewModel::class.java)
        sharedPreferenceManger=SharedPreferenceMangerImpl(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        token= activity?.intent?.getStringExtra("token")
        setSpinners()


        setupSwipeRefresh()
        setupRecycleView()
        if (hasInternetConnection()){
            binding.sToRefresh.isRefreshing=true
            getAllLeads()
        }else{
            Toast.makeText(requireContext(), "no internet ", Toast.LENGTH_SHORT).show()
        }
        setListeners()
        getAllStatuesType()


        return binding.root
    }

    // setup the pagination buttons enable or disable
    private fun setupPagination(totalPage:Int) {
        if (totalPage>pageNumber){
            binding.forwardArrowPagination.isEnabled=true
            binding.forwardArrowPagination.isClickable=true
        }else{
            binding.forwardArrowPagination.isEnabled=false
            binding.forwardArrowPagination.isClickable=false
        }
        if (pageNumber>1){
            binding.backArrowPagination.isEnabled=true
            binding.backArrowPagination.isClickable=true
        }else{
            binding.backArrowPagination.isEnabled=false
            binding.backArrowPagination.isClickable=false
        }

    }

    private fun setupPaginationStatus(totalPage:Int) {
        if (totalPage>pageNumber){
            binding.forwardArrowPaginationStatus.isEnabled=true
            binding.forwardArrowPaginationStatus.isClickable=true
        }else{
            binding.forwardArrowPaginationStatus.isEnabled=false
            binding.forwardArrowPaginationStatus.isClickable=false
        }
        if (pageNumber>1){
            binding.backArrowPaginationStatus.isEnabled=true
            binding.backArrowPaginationStatus.isClickable=true
        }else{
            binding.backArrowPaginationStatus.isEnabled=false
            binding.backArrowPaginationStatus.isClickable=false
        }

    }

    private fun setListeners() {
        // filter by data is the only option until now
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
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE

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

        //filter by tag
        binding.goFilterTag.setOnClickListener {
            val tag =binding.tagSpinner.selectedItem.toString()
            binding.sToRefresh.isRefreshing=true
            homeViewModel.filterByTag(tag).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {

                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE

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
        //filter by interest
        binding.goFilterInterestType.setOnClickListener {
            val tag =binding.interestTypeSpinner.selectedItem.toString()
            binding.sToRefresh.isRefreshing=true
            homeViewModel.filterByInterest(tag).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {

                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE

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
        //filter by request interest
        binding.goFilterRequestInterest.setOnClickListener {
            val tag =binding.requestInterestSpinner.selectedItem.toString()
            binding.sToRefresh.isRefreshing=true
            homeViewModel.filterByRequestInterest(tag).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {

                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE

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

        //filter By type
        binding.goFilterType.setOnClickListener {
            val type =binding.typeSpinner.selectedItem.toString()
            binding.sToRefresh.isRefreshing=true
            homeViewModel.filterByType(type).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (!it.data?.data?.data.isNullOrEmpty()) {

                            leadsAdapter.saveData(it.data?.data?.data!!)
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE

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

        //filter By Source
        binding.goFilterSource.setOnClickListener {
            if (!binding.sourceIdSearch.text.toString().isNullOrEmpty()) {
                val sourceId = binding.sourceIdSearch.text.toString()
                binding.sToRefresh.isRefreshing = true
                homeViewModel.filterBySourceID(sourceId.toInt()).observe(viewLifecycleOwner, {
                    when (it.status) {
                        ApiStatus.SUCCESS -> {
                            if (!it.data?.data?.data.isNullOrEmpty()) {

                                leadsAdapter.saveData(it.data?.data?.data!!)
                                binding.sToRefresh.isRefreshing = false
                                binding.rvLeads.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                                binding.pagination.visibility = View.GONE
                                binding.paginationStatus.visibility = View.GONE

                            } else {
                                binding.rvLeads.visibility = View.INVISIBLE
                                binding.noDataFound.visibility = View.VISIBLE
                                binding.sToRefresh.isRefreshing = false


                            }
                        }
                        ApiStatus.ERROR -> {
                            makeToast(it.message.toString())
                            binding.sToRefresh.isRefreshing = false
                        }
                        ApiStatus.LOADING -> {

                        }
                    }
                })
            }else{
                makeToast("please enter the source number first")
            }
        }
        //filter By Unit Type
        binding.goFilterUnitTypeId.setOnClickListener {
            if (!binding.unitTypeIdSearch.text.toString().isNullOrEmpty()) {
                val unitType = binding.unitTypeIdSearch.text.toString()
                binding.sToRefresh.isRefreshing = true
                homeViewModel.filterByUnitTYpeID(unitType.toInt()).observe(viewLifecycleOwner, {
                    when (it.status) {
                        ApiStatus.SUCCESS -> {
                            if (!it.data?.data?.data.isNullOrEmpty()) {

                                leadsAdapter.saveData(it.data?.data?.data!!)
                                binding.sToRefresh.isRefreshing = false
                                binding.rvLeads.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                                binding.pagination.visibility = View.GONE
                                binding.paginationStatus.visibility = View.GONE

                            } else {
                                binding.rvLeads.visibility = View.INVISIBLE
                                binding.noDataFound.visibility = View.VISIBLE
                                binding.sToRefresh.isRefreshing = false


                            }
                        }
                        ApiStatus.ERROR -> {
                            makeToast(it.message.toString())
                            binding.sToRefresh.isRefreshing = false
                        }
                        ApiStatus.LOADING -> {

                        }
                    }
                })
            }else{
                makeToast("unit type id field can't be null")
            }
        }
        binding.goFilterBudget.setOnClickListener {
            if (!binding.budgetFromSearch.text.toString().isNullOrEmpty()&&!binding.budgetToSearch.text.toString().isNullOrEmpty()) {
                val from = binding.budgetFromSearch.text.toString()
                val to = binding.budgetToSearch.text.toString()
                binding.sToRefresh.isRefreshing = true
                homeViewModel.filterByBudget(from.toLong(), to.toLong()).observe(viewLifecycleOwner, {
                    when (it.status) {
                        ApiStatus.SUCCESS -> {
                            if (!it.data?.data?.data.isNullOrEmpty()) {

                                leadsAdapter.saveData(it.data?.data?.data!!)
                                binding.sToRefresh.isRefreshing = false
                                binding.rvLeads.visibility = View.VISIBLE
                                binding.noDataFound.visibility = View.GONE
                                binding.pagination.visibility = View.GONE
                                binding.paginationStatus.visibility = View.GONE

                            } else {
                                binding.rvLeads.visibility = View.INVISIBLE
                                binding.noDataFound.visibility = View.VISIBLE
                                binding.sToRefresh.isRefreshing = false


                            }
                        }
                        ApiStatus.ERROR -> {
                            makeToast(it.message.toString())
                            binding.sToRefresh.isRefreshing = false
                        }
                        ApiStatus.LOADING -> {

                        }
                    }
                })
            }else{
                makeToast("please enter budget range first")
            }
        }

        binding.goFilterStatus.setOnClickListener {
             statusId=0
            val status=binding.statusSpinner.selectedItem.toString()
            for (item in baseAction?.data!!){
                if (status==item.name+"    ("+item.leadsCount+")"){
                    statusId=item.id
                }
            }
            if (statusId==null){
                statusId=1
            }
            getallLeadsByStatus(statusId)
        }


        //clear filter and return to the ordinary get all leads
        binding.btnClearFilter.setOnClickListener {
            binding.filterLayout.visibility=View.GONE
            binding.filterByTagLayout.visibility=View.GONE
            binding.filterByTypeLayout.visibility=View.GONE
            binding.filterByInterestedTypeLayout.visibility=View.GONE
            binding.filterByRequestInterestLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.filterByStatusLayout.visibility=View.GONE




            binding.sToRefresh.isRefreshing=true
            getAllLeads()
            binding.filterBySpinner.setSelection(0)
        }

        binding.backArrowPagination.setOnClickListener {
            pageNumber -= 1

            binding.sToRefresh.isRefreshing =true
            getAllLeads(pageNumber)
        }
        binding.backArrowPaginationStatus.setOnClickListener {
            pageNumber -= 1

            binding.sToRefresh.isRefreshing =true
            getallLeadsByStatus(statusId,pageNumber)
        }

        binding.forwardArrowPagination.setOnClickListener {
            pageNumber+=1

            binding.sToRefresh.isRefreshing =true
            getAllLeads(pageNumber)

        }
        binding.forwardArrowPaginationStatus.setOnClickListener {
            pageNumber+=1

            binding.sToRefresh.isRefreshing =true
            getallLeadsByStatus(statusId,pageNumber)

        }
    }

    private fun getallLeadsByStatus(statusId: Int) {
        try {
            homeViewModel.getAllLeadsByStatusId(1,sharedPreferenceManger.companyId.toInt(),statusId).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        pageNumber=1
                        if (it.data?.data?.data!=null) {
                            if (it.data.data.total!=null){
                                binding.txtTotalLeadsNumber.text=it.data.data.total.toString()
                            }

                            leadsAdapter.saveData(it.data.data.data)


                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.viewsLayout.visibility=View.VISIBLE


                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.VISIBLE
                            setupPaginationStatus(it.data.data.lastPage)
                            binding.pageNumber.text=pageNumber.toString()

                        }
                    }
                    ApiStatus.ERROR->{
                        makeToast(it.message.toString())
                        binding.sToRefresh.isRefreshing=false
                        binding.pagination.visibility=View.GONE
                        binding.paginationStatus.visibility=View.INVISIBLE
                        binding.viewsLayout.visibility=View.GONE

                        try {

                            if (it.message.toString() == "UnAuthenticated") {
                                sharedPreferenceManger.isLoggedIn = false
                            }
                            startActivity(Intent(requireActivity(), SplashActivity::class.java))
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }

                    }
                    ApiStatus.LOADING->{


                    }
                }
            })
        }catch (ex:Exception){
            makeToast("something went wrong ")

        }
    }
    private fun getallLeadsByStatus(statusId: Int,pageNumber:Int) {
        try {
            homeViewModel.getAllLeadsByStatusId(1,sharedPreferenceManger.companyId.toInt(),statusId,pageNumber).observe(viewLifecycleOwner,{
                when(it.status){
                    ApiStatus.SUCCESS->{
                        if (it.data?.data?.data!=null) {
                            if (it.data.data.total!=null){
                                binding.txtTotalLeadsNumber.text=it.data.data.total.toString()
                            }

                            leadsAdapter.saveData(it.data.data.data)


                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.VISIBLE
                            binding.viewsLayout.visibility=View.VISIBLE


                            binding.noDataFound.visibility=View.GONE
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.VISIBLE
                            setupPaginationStatus(it.data.data.lastPage)
                            binding.pageNumber.text=pageNumber.toString()

                        }
                    }
                    ApiStatus.ERROR->{
                        makeToast(it.message.toString())
                        binding.sToRefresh.isRefreshing=false
                        binding.pagination.visibility=View.INVISIBLE
                        binding.paginationStatus.visibility=View.GONE
                        binding.viewsLayout.visibility=View.GONE

                        try {

                            if (it.message.toString() == "UnAuthenticated") {
                                sharedPreferenceManger.isLoggedIn = false
                            }
                            startActivity(Intent(requireActivity(), SplashActivity::class.java))
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }

                    }
                    ApiStatus.LOADING->{


                    }
                }
            })
        }catch (ex:Exception){
            makeToast("something went wrong ")

        }
    }

    @SuppressLint("ResourceType")
    private fun setSpinners() {

        //years spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.years,android.R.layout.simple_list_item_1).also {
            binding.yearSpinner.adapter=it
        }
        //months spinner
        binding.yearSpinner.onItemSelectedListener=this
        ArrayAdapter.createFromResource(requireContext(),R.array.months,android.R.layout.simple_list_item_1).also {
            binding.monthSpinner.adapter=it
        }
        binding.monthSpinner.onItemSelectedListener=this

        // filters spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.filters,android.R.layout.simple_list_item_1).also {
            binding.filterBySpinner.adapter=it
        }
        binding.filterBySpinner.onItemSelectedListener=this

        //tags Spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.tags_filter,android.R.layout.simple_list_item_1).also {
            binding.tagSpinner.adapter=it
        }
        binding.tagSpinner.onItemSelectedListener=this

        //type Spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.type_filter,android.R.layout.simple_list_item_1).also {
            binding.typeSpinner.adapter=it
        }
        binding.typeSpinner.onItemSelectedListener=this

        //interest Spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.interested_type_filter,android.R.layout.simple_list_item_1).also {
            binding.interestTypeSpinner.adapter=it
        }
        binding.interestTypeSpinner.onItemSelectedListener=this

        //type Spinner
        ArrayAdapter.createFromResource(requireContext(),R.array.request_interest_filter,android.R.layout.simple_list_item_1).also {
            binding.requestInterestSpinner.adapter=it
        }
        binding.requestInterestSpinner.onItemSelectedListener=this
    }

    override fun onResume() {
        super.onResume()
        //enable bottom and top nav cause they arent visible in another fragments
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

    /**
     * get all leads for that company id
     */
    private fun getAllLeads(){
        try {
        homeViewModel.getAllLeads(1,"a",sharedPreferenceManger.companyId.toInt(),sharedPreferenceManger.userToken).observe(viewLifecycleOwner,{
            when(it.status){
                ApiStatus.SUCCESS->{
                    pageNumber=1
                    nostatus=0
                    if (it.data?.data?.data!=null) {
                        if (it.data.data.total!=null){
                            binding.txtTotalLeadsNumber.text=it.data.data.total.toString()
                        }

                        leadsAdapter.saveData(it.data.data.data)


                        binding.sToRefresh.isRefreshing=false
                        binding.rvLeads.visibility=View.VISIBLE
                        binding.viewsLayout.visibility=View.VISIBLE


                        binding.noDataFound.visibility=View.GONE
                        binding.pagination.visibility=View.VISIBLE
                        binding.paginationStatus.visibility=View.GONE
                        setupPagination(it.data.data.lastPage)
                        binding.pageNumber.text=pageNumber.toString()

                    }
                }
                ApiStatus.ERROR->{
                    makeToast(it.message.toString())
                    binding.sToRefresh.isRefreshing=false
                    binding.pagination.visibility=View.INVISIBLE
                    binding.paginationStatus.visibility=View.GONE
                    binding.viewsLayout.visibility=View.GONE

                    try {

                        if (it.message.toString() == "UnAuthenticated") {
                            sharedPreferenceManger.isLoggedIn = false
                        }
                        startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }

                }
                ApiStatus.LOADING->{


                }
            }
        })
    }catch (ex:Exception){
        makeToast("something went wrong ")

        }
    }
    // get all leads with pagination and page number
   private fun getAllLeads(int: Int) {
        try {
            homeViewModel.getAllLeads(
                1,
                "a",
                sharedPreferenceManger.companyId.toInt(),
                sharedPreferenceManger.userToken,int
            ).observe(viewLifecycleOwner, {
                nostatus=0
                when (it.status) {
                    ApiStatus.SUCCESS -> {
                        if (it.data?.data?.data != null) {
                            if (it.data.data.total!=null){
                                binding.txtTotalLeadsNumber.text=it.data.data.total.toString()
                            }

                            leadsAdapter.saveData(it.data.data.data)
                            binding.sToRefresh.isRefreshing = false
                            binding.rvLeads.visibility = View.VISIBLE

                            binding.noDataFound.visibility = View.GONE
                            binding.pagination.visibility = View.VISIBLE
                            binding.paginationStatus.visibility = View.GONE
                            setupPagination(it.data.data.lastPage)
                            binding.pageNumber.text=pageNumber.toString()
                            binding.viewsLayout.visibility=View.VISIBLE






                        }
                    }
                    ApiStatus.ERROR -> {
                        makeToast(it.message.toString())
                        binding.sToRefresh.isRefreshing = false
                        binding.pagination.visibility=View.INVISIBLE
                        binding.paginationStatus.visibility=View.GONE
                        binding.viewsLayout.visibility=View.GONE


                    }
                    ApiStatus.LOADING -> {


                    }
                }
            })
        } catch (ex:Exception) {
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

        /***
         * enable one filter and disable the others
         */
        if (binding.filterBySpinner.selectedItem=="Date"){
            binding.filterLayout.visibility=View.VISIBLE
            binding.filterByTagLayout.visibility=View.GONE
            binding.filterByTypeLayout.visibility=View.GONE
            binding.filterByInterestedTypeLayout.visibility=View.GONE
            binding.filterByRequestInterestLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE





        }
        else if (binding.filterBySpinner.selectedItem=="Tag"){
            binding.filterByTagLayout.visibility=View.VISIBLE
            binding.filterLayout.visibility=View.GONE
            binding.filterByTypeLayout.visibility=View.GONE
            binding.filterByInterestedTypeLayout.visibility=View.GONE
            binding.filterByRequestInterestLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE





        } else if (binding.filterBySpinner.selectedItem == "Type"){
            binding.filterByTypeLayout.visibility=View.VISIBLE
            binding.filterLayout.visibility=View.GONE
            binding.filterByTagLayout.visibility=View.GONE
            binding.filterByInterestedTypeLayout.visibility=View.GONE
            binding.filterByRequestInterestLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE





        }else if (binding.filterBySpinner.selectedItem == "Interested Type"){
            binding.filterByInterestedTypeLayout.visibility=View.VISIBLE
            binding.filterLayout.visibility=View.GONE
            binding.filterByTagLayout.visibility=View.GONE
            binding.filterByTypeLayout.visibility=View.GONE
            binding.filterByRequestInterestLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE





        }else if (binding.filterBySpinner.selectedItem == "Request Interest"){
            binding.filterByRequestInterestLayout.visibility=View.VISIBLE
            binding.filterLayout.visibility=View.GONE
            binding.filterByTagLayout.visibility=View.GONE
            binding.filterByTypeLayout.visibility=View.GONE
            binding.filterByInterestedTypeLayout.visibility=View.GONE
            binding.filterBySourceIdLayout.visibility=View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE





        } else if (binding.filterBySpinner.selectedItem == "Source ID") {
            binding.filterBySourceIdLayout.visibility = View.VISIBLE
            binding.filterByRequestInterestLayout.visibility = View.GONE
            binding.filterLayout.visibility = View.GONE
            binding.filterByTagLayout.visibility = View.GONE
            binding.filterByTypeLayout.visibility = View.GONE
            binding.filterByInterestedTypeLayout.visibility = View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE




        } else if (binding.filterBySpinner.selectedItem == "Unit Type") {
            binding.filterByUnitTypeIdLayout.visibility = View.VISIBLE
            binding.filterBySourceIdLayout.visibility = View.GONE
            binding.filterByRequestInterestLayout.visibility = View.GONE
            binding.filterLayout.visibility = View.GONE
            binding.filterByTagLayout.visibility = View.GONE
            binding.filterByTypeLayout.visibility = View.GONE
            binding.filterByInterestedTypeLayout.visibility = View.GONE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE



        }
        else if (binding.filterBySpinner.selectedItem == "Budget") {
            binding.filterByBudgetLayout.visibility = View.VISIBLE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterBySourceIdLayout.visibility = View.GONE
            binding.filterByRequestInterestLayout.visibility = View.GONE
            binding.filterLayout.visibility = View.GONE
            binding.filterByTagLayout.visibility = View.GONE
            binding.filterByTypeLayout.visibility = View.GONE
            binding.filterByInterestedTypeLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE
            binding.filterByStatusLayout.visibility=View.GONE


        }else if(binding.filterBySpinner.selectedItem=="Status"){
            binding.filterByStatusLayout.visibility=View.VISIBLE
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterBySourceIdLayout.visibility = View.GONE
            binding.filterByRequestInterestLayout.visibility = View.GONE
            binding.filterLayout.visibility = View.GONE
            binding.filterByTagLayout.visibility = View.GONE
            binding.filterByTypeLayout.visibility = View.GONE
            binding.filterByInterestedTypeLayout.visibility = View.GONE
            binding.viewsLayout.visibility=View.GONE

        } else
        {
            binding.filterByBudgetLayout.visibility = View.GONE
            binding.filterByUnitTypeIdLayout.visibility = View.GONE
            binding.filterBySourceIdLayout.visibility = View.GONE
            binding.filterByRequestInterestLayout.visibility = View.GONE
            binding.filterLayout.visibility = View.GONE
            binding.filterByTagLayout.visibility = View.GONE
            binding.filterByTypeLayout.visibility = View.GONE
            binding.filterByInterestedTypeLayout.visibility = View.GONE
            binding.filterByStatusLayout.visibility=View.GONE

            getAllLeads()
            binding.viewsLayout.visibility=View.VISIBLE

        }
    }

    private fun getAllStatuesType() {
        homeViewModel.getAllStatusToFilter(sharedPreferenceManger.companyId.toInt()).observe(viewLifecycleOwner,{
            when(it.status) {
                ApiStatus.SUCCESS -> {
                        baseAction=it.data
                    for (item in it.data!!.data){
                        list.add(item.name+"    ("+item.leadsCount+")")
                    }
                    setActionsSpinner()
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        })
    }

    private fun setActionsSpinner() {
        ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,list.toTypedArray()).also {
            it.setDropDownViewResource(android.R.layout.simple_list_item_1)
            binding.statusSpinner.adapter=it
        }
        list.clear()


        binding.statusSpinner.onItemSelectedListener=this
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
                            binding.pagination.visibility=View.GONE
                            binding.paginationStatus.visibility=View.GONE
                            binding.viewsLayout.visibility=View.GONE

                        }else{
                            binding.sToRefresh.isRefreshing=false
                            binding.rvLeads.visibility=View.INVISIBLE
                            binding.noDataFound.visibility=View.VISIBLE
                            binding.viewsLayout.visibility=View.GONE

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