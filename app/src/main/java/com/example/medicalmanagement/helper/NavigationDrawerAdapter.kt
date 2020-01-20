package com.example.medicalmanagement.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.helper.pojo.NavDrawerItem

class NavigationDrawerAdapter(private val context: Context, internal var data: List<NavDrawerItem>) :
    RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    var selectedPosition: Int = 0

    init {
        inflater = LayoutInflater.from(context)
        selectedPosition = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.nav_drawer_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = data[position]
        holder.title.text = current.title
        // set the animation from letf to Right

        holder.itemBg.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
        }

        holder.imageViewLogo.setImageResource(current.photo)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var imageViewLogo: ImageView
        var itemBg: RelativeLayout

        init {
            title = itemView.findViewById<View>(R.id.title) as TextView
            imageViewLogo = itemView.findViewById<View>(R.id.imageViewLogo) as ImageView
            itemBg = itemView.findViewById(R.id.itemBg)
        }
    }
}
