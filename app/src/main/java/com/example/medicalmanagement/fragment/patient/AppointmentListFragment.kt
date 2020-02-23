package com.example.medicalmanagement.fragment.patient

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.activity.LoginActivity
import com.example.medicalmanagement.adapter.DoctorRegisterAdapter
import com.example.medicalmanagement.adapter.PatientAppointmentAdapter
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao
import com.example.medicalmanagement.db.dao.PatientAppointmentDao
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.db.table.PatientAppointmentTable
import com.example.medicalmanagement.db.table.PatientRegisterTable
import com.example.medicalmanagement.db.table.ScheduleTime
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AppointmentListFragment : Fragment(),PatientAppointmentAdapter.ListAdapterListener {
    private lateinit var patientDetails: PatientRegisterTable
    var TAG = AppointmentListFragment::class.java.simpleName.toString()
    lateinit var fab: FloatingActionButton
    internal lateinit var recycleview: RecyclerView
    lateinit var list: MutableList<PatientAppointmentTable>
    lateinit var appDatabase: AppDatabase
    lateinit var patientAppointmentDao: PatientAppointmentDao
    lateinit var doctorRegisterAdapter: PatientAppointmentAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_doctor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        appDatabase = AppDatabase.getDatabase(activity!!)
        patientAppointmentDao=appDatabase.patientAppointmentDao()
        list=ArrayList()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = LinearLayoutManager(activity)

        var bundle =arguments
        if (bundle!=null){
            patientDetails= bundle.getSerializable(Constants.patientList) as PatientRegisterTable
//            var image= Base64.decode(patientDetails.image, Base64.DEFAULT);
//            commonMethods.loadImage(image,companyimage)
        }

        list = patientAppointmentDao.getPatient(patientDetails.phone!!) as MutableList<PatientAppointmentTable>
        Log.e(TAG,"insertdataaaa " + patientAppointmentDao.getall().size)
        setAdapter(list as ArrayList<PatientAppointmentTable>)

        fab.setOnClickListener {

            setfragment(PatientAppintmentFragment(),patientDetails)
        }

    }

    //set adapter
    private fun setAdapter(list: ArrayList<PatientAppointmentTable>) {

        if (list.size > 0) {
            doctorRegisterAdapter = PatientAppointmentAdapter(list,activity!!,this)
            recycleview.adapter = doctorRegisterAdapter
        } else {

        }
    }

    private fun setfragment(_fragment: Fragment,doctorList: PatientRegisterTable) {
        var bundle =Bundle()
        bundle.putSerializable(Constants.patientList,doctorList)
        val fm = activity!!.supportFragmentManager
        _fragment.arguments=bundle
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onClickButton(position: Int, list: PatientAppointmentTable) {

        val builder = AlertDialog.Builder(CommonMethods.context)
        builder.setMessage("Are you sure want to Delete?")
        builder.setPositiveButton("Yes") { dialog, id ->
            patientAppointmentDao.delete(list.id!!)
            doctorRegisterAdapter.removeItem(position)
        }
        builder.setNegativeButton("No") { dialog, id -> dialog.dismiss() }
        builder.create().show()


    }

    override fun onClickButtonInfo(position: Int, list: PatientAppointmentTable) {
        addNewdialog(list)
    }

    fun addNewdialog(list: PatientAppointmentTable){
        val builder: Dialog = Dialog(activity!!)
        val inflater = layoutInflater
        builder.setTitle("With RatingBar")
        val dialogLayout: View = inflater.inflate(R.layout.fragment_new_message, null)
        builder.setContentView(dialogLayout)
        val time = builder.findViewById<TextView>(R.id.time)
        val submit = builder.findViewById<TextView>(R.id.submit)
        time.setText(list.suggestion)
        submit.setOnClickListener {

                builder.dismiss()

        }

        builder.show()
    }
}
