package com.example.medicalmanagement.adapter

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.table.PatientAppointmentTable
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.Constants

import java.util.ArrayList

class PatientAppointmentAdapter(private var listItems: ArrayList<PatientAppointmentTable>, private val context: Context,
                                private val mListener: ListAdapterListener) : RecyclerView.Adapter<PatientAppointmentAdapter.ViewHolder>() {

    private val TAG = "IOMreport"
    private val srchlist: MutableList<PatientAppointmentTable>
    internal var commonMethods: CommonMethods
    internal var appDatabase: AppDatabase
    init {
        commonMethods = CommonMethods(context)
        this.srchlist = listItems
        appDatabase = AppDatabase.getDatabase(context)
    }


    interface ListAdapterListener { // create an interface
        fun onClickButton(position: Int, list: PatientAppointmentTable)  // create callback function
        fun onClickButtonInfo(position: Int, list: PatientAppointmentTable)  // create callback function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_appointment_list_item, parent, false))
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val list = listItems[position]

//            commonMethods.loadImageSquare(context, viewHolder.image, url,R.drawable.ic_iom_list)

        var time=""
        for ( timeModel in list.time!!){
            if (time==""){
                time=timeModel
            }else{
                time=time+","+timeModel
            }
        }
        Log.e(TAG," time "+ time)
        viewHolder.doctortime.text=time!!
        viewHolder.doctor_mail.text=list.email
            viewHolder.Doctor_Name.text=list.dName
        viewHolder.specialist.text=list.specialist
            viewHolder.doctor_number.text=list.phone

        viewHolder.relativeLayout.setOnClickListener {
            if (position != -1) {
                Log.e(TAG, "POSITION: $position")
                // use callback function to Return the Position
//                mListener.onClickButton(position, list)
            }
        }

        viewHolder.message.setOnClickListener {
            mListener.onClickButtonInfo(position, list)
        }

        viewHolder.delete.setOnClickListener {
            mListener.onClickButton(position, list)
        }
    }

    fun removeItem(positon:Int){
        listItems.removeAt(positon)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      lateinit  var Doctor_Name:TextView
        lateinit var doctor_number:TextView
        lateinit var doctor_mail:TextView
        lateinit var specialist:TextView
        lateinit var doctortime:TextView
        internal  var relativeLayout: CardView
        lateinit var delete:ImageView
        lateinit var message:ImageView
        init {
            Doctor_Name=itemView.findViewById(R.id.Doctor_Name)
            doctor_number=itemView.findViewById(R.id.doctor_number)
            doctor_mail=itemView.findViewById(R.id.doctor_mail)
            specialist=itemView.findViewById(R.id.specialist)
            doctortime=itemView.findViewById(R.id.doctortime)
            relativeLayout=itemView.findViewById(R.id.real)
            delete=itemView.findViewById(R.id.delete)
            message=itemView.findViewById(R.id.message)

        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
//                val charString = charSequence.toString()
//                if (charString.isEmpty()) {
//                    listItems = srchlist
//                } else {
//                    val filteredList = ArrayList<IomReportTable>()
//                    for (androidVersion in srchlist) {
//                        if (Constants.searchBy == "name") {
//                            Log.e(TAG, "search by : " + Constants.searchBy)
//                            if (androidVersion.consignment!!.toLowerCase().contains(charString)||
//                                    androidVersion.courierCompany!!.toLowerCase().contains(charString)) {
//                                filteredList.add(androidVersion)
//                            }
//                        } else {
//                            Log.e(TAG, "search by : " + Constants.searchBy)
//                            if (androidVersion.consignment!!.toLowerCase().contains(charString)) {
//                                filteredList.add(androidVersion)
//                            }
//                        }
//
//                    }
//                    listItems = filteredList
//                }
//                val filterResults = Filter.FilterResults()
//                filterResults.values = listItems
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
//                listItems = filterResults.values as ArrayList<PatientAppointmentTable>
//                notifyDataSetChanged()
//            }
//        }
//    }

}
