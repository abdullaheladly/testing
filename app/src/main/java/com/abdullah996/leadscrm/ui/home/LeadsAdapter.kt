package com.abdullah996.leadscrm.ui.home

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.leadscrm.R
import com.abdullah996.leadscrm.model.leeds.Leads
import com.abdullah996.leadscrm.utill.DiffUtilCallBack

class LeadsAdapter(onLeadsClickListener: OnLeadsClickListener):RecyclerView.Adapter<LeadsAdapter.MyViewHolder>() {

    private var leadsList= emptyList<Leads>()
    private var onClickListener=onLeadsClickListener
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.info_card,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txt_name).text = leadsList[position].name.toString()
        holder.itemView.findViewById<TextView>(R.id.txt_phone).text = leadsList[position].phones.toString()
        holder.itemView.findViewById<TextView>(R.id.txt_email).text = leadsList[position].email.toString()
        holder.itemView.findViewById<TextView>(R.id.details).text = leadsList[position].notes.toString()
      //  holder.itemView.findViewById<TextView>(R.id.txt_status).text = leadsList[position].st.toString()
        holder.itemView.findViewById<TextView>(R.id.editInfo).setOnClickListener {
            onClickListener.onEditInfoClick(leadsList[position])
        }
        holder.itemView.findViewById<TextView>(R.id.addaction).setOnClickListener {
            onClickListener.onAddActionClick(leadsList[position].id)
        }
        holder.itemView.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
            onClickListener.onDeleteLeadClick(leadsList[position].id)
        }
        if (leadsList[position].status!=null){
            if (!leadsList[position].status.name.isNullOrEmpty()){
                holder.itemView.findViewById<TextView>(R.id.txt_action).text=leadsList[position].status.name.toString()
                holder.itemView.findViewById<TextView>(R.id.txt_status).text=leadsList[position].status.name.toString()
                holder.itemView.findViewById<CanvasView>(R.id.line_without_status).visibility=View.GONE
                holder.itemView.findViewById<TextView>(R.id.txt_no_status).visibility=View.GONE
            }else{
                holder.itemView.findViewById<TextView>(R.id.txt_action).text=""
                holder.itemView.findViewById<TextView>(R.id.txt_status).text=""
                holder.itemView.findViewById<CanvasView>(R.id.line_without_status).visibility=View.VISIBLE
                holder.itemView.findViewById<TextView>(R.id.txt_no_status).visibility=View.VISIBLE
            }
            if (!leadsList[position].status.comment.isNullOrEmpty()){
                holder.itemView.findViewById<TextView>(R.id.txt_comment).text=leadsList[position].status.comment.toString()

            }else{
                holder.itemView.findViewById<TextView>(R.id.txt_comment).text=""

            }
        }else{
            holder.itemView.findViewById<CanvasView>(R.id.line_without_status).visibility=View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.txt_no_status).visibility=View.VISIBLE
            holder.itemView.findViewById<TextView>(R.id.txt_action).text=""
            holder.itemView.findViewById<TextView>(R.id.txt_status).text=""
            holder.itemView.findViewById<TextView>(R.id.txt_comment).text=""
        }


    }

    override fun getItemCount(): Int {
       return  leadsList.size
    }

    fun saveData(newLeadsList:List<Leads>){
        val leadsListDiffUtil=DiffUtilCallBack(leadsList,newLeadsList)
        val diffUtilResult=DiffUtil.calculateDiff(leadsListDiffUtil)
        leadsList=newLeadsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}