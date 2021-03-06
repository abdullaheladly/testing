package com.abdullah996.leadscrm.ui.home

import com.abdullah996.leadscrm.model.leeds.Leads

interface OnLeadsClickListener {
    fun onEditInfoClick(lead: Leads)
    fun onAddActionClick(id: Int)
    fun onDeleteLeadClick(id: Int)
    fun onStatusItemClick(id: Int)
    fun onPhoneTextClick(phone:String)
    fun onEmailTextClick(phone:String)
}