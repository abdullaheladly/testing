package com.abdullah996.leadscrm.ui.notifications

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.model.notifications.Notification
import com.abdullah996.leadscrm.utill.DiffUtilCallBack
import com.squareup.picasso.Picasso


class NotificationsAdapter(image:String):RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>(){
    private var notificationsList= emptyList<Notification>()
    private val logo =image

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notifications_cart,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txt_notifications_text).text=notificationsList[position].data.msg
        holder.itemView.findViewById<TextView>(R.id.txt_notifications_date).text=notificationsList[position].createdAt
        if (notificationsList[position].readAt!=null){
            holder.itemView.setBackgroundColor(Color.WHITE)
        }else{
            holder.itemView.setBackgroundColor(Color.LTGRAY)

        }
        if (logo=="null"){
                    // keep current logo as it is
        }else{
            val uri=Uri.parse(logo)
            Picasso.get().load(uri).into(holder.itemView.findViewById<ImageView>(R.id.notifications_image))
        }
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