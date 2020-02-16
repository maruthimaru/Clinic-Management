package com.example.medicalmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.table.ScheduleTime

class SchedulePatienttimeAdapter(private val list:ArrayList<ScheduleTime>, private val context:Context,
                                 private val mListener: ListAdapterListener): RecyclerView.Adapter<SchedulePatienttimeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textViewTime:TextView

        init {
            textViewTime=itemView.findViewById(R.id.textViewTime)
        }

    }

    fun getClickedStatus():List<String>{
        val clickedList=ArrayList<String>()
        var timing=""
        for (model in list)
            if(model.clickStatus==1) {
                clickedList.add(model.timeing)
                if (timing==""){
                    timing=model.timeing
                }else{
                    timing=timing+","+model.timeing
                }
            }
        return clickedList
    }

    interface ListAdapterListener{
        fun onIemClick()
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
        if (model.clickStatus==1) {
            holder.textViewTime.setBackground(context.resources.getDrawable(R.drawable.button_background_40))
            holder.textViewTime.setTextColor(context.resources.getColor(R.color.white))
        }else if (model.clickStatus==2) {
            holder.textViewTime.setBackground(context.resources.getDrawable(R.drawable.button_background_red_40))
            holder.textViewTime.setTextColor(context.resources.getColor(R.color.white))
        }else{
            holder.textViewTime.setBackground(context.resources.getDrawable(R.drawable.borderblack))
            holder.textViewTime.setTextColor(context.resources.getColor(R.color.black))
        }

        holder.textViewTime.setOnClickListener {
//            mListener.onIemClick()
            if (model.clickStatus==0) {
                model.clickStatus = 1
                holder.textViewTime.setBackground(context.resources.getDrawable(R.drawable.button_background_40))
                holder.textViewTime.setTextColor(context.resources.getColor(R.color.white))
            }else if (model.clickStatus==1){
                model.clickStatus = 0
                holder.textViewTime.setBackground(context.resources.getDrawable(R.drawable.borderblack))
                holder.textViewTime.setTextColor(context.resources.getColor(R.color.black))
            }else{
                Toast.makeText(context,"Already booked" ,Toast.LENGTH_SHORT).show()
            }
        }

    }
}