package com.abdullah996.leadscrm.ui.actions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.utill.DiffUtilCallBack

class ActionsAdapter():RecyclerView.Adapter<ActionsAdapter.MyViewHolder>() {


    //replace string with action model class
    private var actionsList= emptyList<String>()
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.action_card,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txt_action_action)
        holder.itemView.findViewById<TextView>(R.id.txt_action_action)
    }

    override fun getItemCount(): Int {
        return actionsList.size
    }


    //replace string with actionsModel class
    fun saveData(newActionsList:List<String>){
        val leadsListDiffUtil= DiffUtilCallBack(actionsList,newActionsList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        actionsList=newActionsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}