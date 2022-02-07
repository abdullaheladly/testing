package com.abdullah996.leadscrm.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.model.getstatus.Actions
import com.abdullah996.leadscrm.model.notifications.Notification
import com.abdullah996.leadscrm.utill.DiffUtilCallBack


class NotificationsAdapter:RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(){
    private var notificationsList= emptyList<Notification>()


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notifications_cart,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txt_notifications_text).text=notificationsList[position].data.msg
        holder.itemView.findViewById<TextView>(R.id.txt_notifications_date).text=notificationsList[position].createdAt
    }

    override fun getItemCount(): Int {
        return notificationsList.size
    }

    fun saveData(newActionsList:List<Notification>){
        val leadsListDiffUtil= DiffUtilCallBack(notificationsList,newActionsList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        notificationsList=newActionsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}