package com.abdullah996.leadscrm.ui.home

import com.abdullah996.leadscrm.model.leeds.Leads

interface OnLeadsClickListener {
    fun onEditInfoClick(lead: Leads)
    fun onAddActionClick(id: Int)
}