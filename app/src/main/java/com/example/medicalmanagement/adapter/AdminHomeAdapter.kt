package com.example.medicalmanagement.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.helper.pojo.AdminHomeModel


class AdminHomeAdapter(private val listItems: List<AdminHomeModel>, private val context: Context, private val mListener: AdminHomeAdapter.ListAdapterListener)//        animation = new CustomAnimation(context);
    : RecyclerView.Adapter<AdminHomeAdapter.ViewHolder>() {
    private val TAG = "Misreport"

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.admin_home_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listItems[position]
        holder.my.text = list.name
//        if (list.image != 1) {
            holder.image.setImageResource(list.image)
//        }


        //animation.setAnimation(viewHolder.itemView, position);


        holder.relativeLayouthome.setOnClickListener {
            Log.e(TAG, "POSITION: $position")
            if (position != -1 || position.toString() != null) {

                // use callback function to Return the Position
                mListener.onClickButton(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    interface ListAdapterListener { // create an interface
        fun onClickButton(position: Int)  // create callback function
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var my: TextView
        internal var image: ImageView


        internal var relativeLayouthome: RelativeLayout

        init {

            my = itemView.findViewById(R.id.entries)
            image = itemView.findViewById(R.id.imgdrawable)
            relativeLayouthome = itemView.findViewById(R.id.relativeLayoutHome)
        }
    }
}


