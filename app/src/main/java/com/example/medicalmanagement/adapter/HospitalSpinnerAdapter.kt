package com.example.medicalmanagement.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.medicalmanagement.db.table.HospitalRegisterTable


class HospitalSpinnerAdapter(context: Context, resource: Int, values: MutableList<HospitalRegisterTable>) : ArrayAdapter<HospitalRegisterTable>
(context, resource, values) {

    lateinit var values:MutableList<HospitalRegisterTable>

    init {
        this.values=values
    }
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): HospitalRegisterTable? {
        return values[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.text = values[position].name

        // And finally return your dynamic (or custom) view for each spinner item
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].name

        return label
    }

}