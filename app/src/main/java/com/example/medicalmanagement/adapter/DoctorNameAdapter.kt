package com.example.medicalmanagement.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import java.util.*

class DoctorNameAdapter(internal var context: Context, internal var resource: Int, internal var textViewResourceId: Int, internal var items: List<DoctorRegisterTable>,
                        private val mListener: ItemSelectedLisitner) : ArrayAdapter<DoctorRegisterTable>(context, resource, textViewResourceId, items) {
    internal var tempItems= ArrayList<DoctorRegisterTable>()
    internal var suggestions= ArrayList<DoctorRegisterTable>()
    internal var TAG = DoctorNameAdapter::class.java.simpleName

    internal var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence? {
//            Log.e(TAG, "convertResultToString: $resultValue")
            var str: String? = null

                str = (resultValue as DoctorRegisterTable).name

            mListener.onClick(resultValue as DoctorRegisterTable)
            mListener.onItemClick(resultValue.name!!)
//            Log.e(TAG, "convertResultToString: " + str!!)
            return str
        }

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            if (constraint != null) {
                suggestions.clear()
                var check = ""
//                Log.e(TAG, "checkConvert: ")
                for (employeeModel in tempItems) {

                        var searchtype=""
                            searchtype=employeeModel.name!!


                    if (searchtype.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(employeeModel)
                        check = "1"
//                        Log.e(TAG, "checkConvert: in")
                    } else {
//                        Log.e(TAG, "checkConvert: not")
                        check = "0"
                    }

                }
                val filterResults = Filter.FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                return filterResults
            } else {
                return Filter.FilterResults()
            }
        }
        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
            mListener.onClickCountEmp(results!!.count)
            if (results != null && results.count > 0) {
                val filterList = results.values as ArrayList<DoctorRegisterTable>
                clear()
                try {
                    for (employeeModel in filterList) {
                        add(employeeModel)
                        notifyDataSetChanged()
//                        Log.e(TAG, "publishResults: " + employeeModel.name)
                        //                    mListener.onItemClickSupplier(employeeModel.getEmpId());
                    }
                }catch (e:ConcurrentModificationException){

                }

            } else {
//                Log.e(TAG, "publishResults null: " + results.count)
            }
        }
    }

    interface ItemSelectedLisitner {
        fun onItemClick(id: String)
        fun onClickCountEmp(count: Int)
        fun onClick(items: DoctorRegisterTable)
    }

    init {
        tempItems = ArrayList(items) // this makes the difference.
        suggestions = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.manager_item, parent, false)
        }
        val employeeModel = items[position]
        if (employeeModel != null) {
            val managerName = view!!.findViewById<TextView>(R.id.name)
                    managerName.text = employeeModel.name

            //            managerName.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClickSupplier(View v) {
            //                    mListener.onItemClickSupplier(employeeModel.getEmpId());
            //                    Log.e(TAG, "onClickSupplier: empId : "+ employeeModel.getEmpId());
            //                }
            //            });

        }
        return view!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }
}