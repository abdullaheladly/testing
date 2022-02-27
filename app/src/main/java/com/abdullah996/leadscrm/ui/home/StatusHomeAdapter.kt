package com.abdullah996.leadscrm.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.model.statusmodel.StatusModel
import com.abdullah996.leadscrm.model.statusmodel.StatusModelResponse
import com.abdullah996.leadscrm.utill.DiffUtilCallBack

class StatusHomeAdapter(onLeadsClickListener: OnLeadsClickListener): RecyclerView.Adapter<StatusHomeAdapter.MyViewHolder>() {

    private var statusList= emptyList<StatusModel>()
    private var onClickListener=onLeadsClickListener

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusHomeAdapter.MyViewHolder {
        return StatusHomeAdapter.MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.status_card, parent, false)
        )

    }

    override fun onBindViewHolder(holder: StatusHomeAdapter.MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txt_totalLeadsStatus).text=statusList[position].name.toString()
        holder.itemView.findViewById<TextView>(R.id.txt_totalLeadsNumberStatus).text=statusList[position].leadsCount.toString()
        holder.itemView.setOnClickListener {
            onClickListener.onStatusItemClick(statusList[position].id)
        }
    }

    override fun getItemCount(): Int {
       return statusList.size
    }

    fun saveData(newStatusList:List<StatusModel>){
        val leadsListDiffUtil= DiffUtilCallBack(statusList,newStatusList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        statusList=newStatusList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}