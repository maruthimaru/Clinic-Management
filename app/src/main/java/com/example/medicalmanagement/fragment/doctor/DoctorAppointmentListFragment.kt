package com.example.medicalmanagement.fragment.doctor

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

class DoctorAppointmentListFragment : Fragment(),PatientAppointmentAdapter.ListAdapterListener {
    private lateinit var patientDetails: DoctorRegisterTable
    var TAG = DoctorAppointmentListFragment::class.java.simpleName.toString()
    lateinit var fab: FloatingActionButton
    internal lateinit var recycleview: RecyclerView
    lateinit var list: MutableList<PatientAppointmentTable>
    lateinit var appDatabase: AppDatabase
    lateinit var patientAppointmentDao: PatientAppointmentDao
    lateinit var doctorRegisterAdapter: PatientAppointmentAdapter
    lateinit var commonMethods: CommonMethods

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_doctor_details, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        commonMethods= CommonMethods(activity!!)
        appDatabase = AppDatabase.getDatabase(activity!!)
        patientAppointmentDao=appDatabase.patientAppointmentDao()
        list=ArrayList()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = LinearLayoutManager(activity)

        var bundle =arguments
        if (bundle!=null){
            patientDetails= bundle.getSerializable(Constants.doctorList) as DoctorRegisterTable
//            var image= Base64.decode(patientDetails.image, Base64.DEFAULT);
//            commonMethods.loadImage(image,companyimage)
        }

        list = patientAppointmentDao.getAppointPatient(patientDetails.id!!.toString()) as MutableList<PatientAppointmentTable>
        Log.e(TAG,"insertdataaaa " + patientAppointmentDao.getall().size)
        setAdapter(list as ArrayList<PatientAppointmentTable>)
        fab.visibility=View.GONE
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
        bundle.putSerializable(Constants.doctorList,doctorList)
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
        val dialogLayout: View = inflater.inflate(R.layout.fragment_inform_message, null)
        builder.setContentView(dialogLayout)
        val time = builder.findViewById<EditText>(R.id.time)
        val submit = builder.findViewById<TextView>(R.id.submit)
        val reject = builder.findViewById<TextView>(R.id.reject)
        val approve = builder.findViewById<TextView>(R.id.approve)
        submit.setOnClickListener {

            val textString=time.text.toString()
            if (textString.length>0){
                time.setError(null)
                list.suggestion=textString
                patientAppointmentDao.update(list)
                commonMethods.sendSMS(list.phone,textString)
                builder.dismiss()
            }else{
                time.setError("Give a suggestion")
                time.requestFocus()
            }

        }

        reject.setOnClickListener {

            val textString=time.text.toString()
            if (textString.length>0){
                time.setError(null)
                list.suggestion=textString
                list.isRejects=true
                patientAppointmentDao.update(list)
                commonMethods.sendSMS(list.phone,textString)
                builder.dismiss()
            }else{
                time.setError("Give a Reaject reason")
                time.requestFocus()
            }

        }

        approve.setOnClickListener {
            commonMethods.sendSMS(list.phone,"Your appointment has accepted on ${list.date +" " + list.time}")
            builder.dismiss()

        }

        builder.show()
    }
}
