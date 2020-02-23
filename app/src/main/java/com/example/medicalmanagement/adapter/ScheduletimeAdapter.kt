package com.example.medicalmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.table.ScheduleTime

class ScheduletimeAdapter(private val list:ArrayList<ScheduleTime>,private val context:Context,
                          private val mListener: ListAdapterListener): RecyclerView.Adapter<ScheduletimeAdapter.ViewHolder>() {

    var clickStatus:Boolean = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textViewTime:TextView

        init {
            textViewTime=itemView.findViewById(R.id.textViewTime)
        }

    }

    fun removeItem(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }

    interface ListAdapterListener{
        fun onIemClick(position: Int,model:ScheduleTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_time_schedule,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model=list[position]

        holder.textViewTime.setText(model.timeing)

        holder.textViewTime.setOnClickListener {
            mListener.onIemClick(position,model)
        }

    }
}