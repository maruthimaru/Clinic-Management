package com.example.medicalmanagement.fragment.admin

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.adapter.ScheduletimeAdapter
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.ScheduleTimeDao
import com.example.medicalmanagement.db.table.ScheduleTime
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ScheduleTimeFragment : Fragment(), ScheduletimeAdapter.ListAdapterListener {

    private val TAG = ScheduleTimeFragment::class.java.simpleName
    private lateinit var list: MutableList<ScheduleTime>
    lateinit var fab: FloatingActionButton
    internal lateinit var recycleview: RecyclerView
    lateinit var appDatabase: AppDatabase
    lateinit var scheduleTimeDao: ScheduleTimeDao
    lateinit var scheduletimeAdapter: ScheduletimeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule_time,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        appDatabase = AppDatabase.getDatabase(activity!!)
        scheduleTimeDao=appDatabase.schudleTimeDao()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = LinearLayoutManager(activity)

        list = scheduleTimeDao.getTime() as MutableList<ScheduleTime>
        Log.e(TAG,"insertdataaaa " + scheduleTimeDao.getTime().size)

        setAdapter(list as ArrayList<ScheduleTime>)

        fab.setOnClickListener {

            addNewdialog()
        }

    }


    fun addNewdialog(){
        val builder: Dialog = Dialog(activity!!)
        val inflater = layoutInflater
        builder.setTitle("With RatingBar")
        val dialogLayout: View = inflater.inflate(R.layout.fragment_new_time, null)
        builder.setContentView(dialogLayout)
        val time = builder.findViewById<EditText>(R.id.time)
        val submit = builder.findViewById<TextView>(R.id.submit)
        submit.setOnClickListener {
            val stringTime=time.text.toString()
            if (stringTime.length>0){

                val scheduleTime=ScheduleTime(stringTime)
                scheduleTimeDao.insert(scheduleTime)

                list = scheduleTimeDao.getTime() as MutableList<ScheduleTime>
                Log.e(TAG,"insertdataaaa " + scheduleTimeDao.getTime().size)
                setAdapter(list as ArrayList<ScheduleTime>)
            builder.dismiss()
            }else{
                time.requestFocus()
                time.setError("Enter time")
//                Toast.makeText(activity!!,"Enter Time ",Toast.LENGTH_SHORT).show()
            }
        }

        builder.show()
    }

    //set adapter
    private fun setAdapter(list: ArrayList<ScheduleTime>) {

        if (list.size > 0) {
            scheduletimeAdapter = ScheduletimeAdapter(list,activity!!,this)
            recycleview.adapter = scheduletimeAdapter
        } else {

        }
    }

    override fun onIemClick(position: Int,model:ScheduleTime) {
        AlertDialog.Builder(activity!!)
                .setMessage("Are you sure want to delete?")
                .setPositiveButton("yes",DialogInterface.OnClickListener { dialog, which ->
                    scheduleTimeDao.delete(model)
                    scheduletimeAdapter.removeItem(position)
                    dialog.dismiss()
                })
                .setNegativeButton("No" , DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                }).show()
    }

}