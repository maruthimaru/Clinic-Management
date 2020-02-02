package com.example.medicalmanagement.fragment.admin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.activity.LoginActivity
import com.example.medicalmanagement.adapter.DoctorRegisterAdapter
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.helper.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DoctorFragment : Fragment(),DoctorRegisterAdapter.ListAdapterListener {
    var TAG = DoctorFragment::class.java.simpleName.toString()
    lateinit var fab: FloatingActionButton
    internal lateinit var recycleview: RecyclerView
    lateinit var list: MutableList<DoctorRegisterTable>
    lateinit var appDatabase: AppDatabase
    lateinit var doctorRegisterDao: DoctorRegisterDao
    lateinit var doctorRegisterAdapter: DoctorRegisterAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_doctor_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        appDatabase = AppDatabase.getDatabase(activity!!)
        doctorRegisterDao=appDatabase.doctorregisterdao()
        list=ArrayList()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = LinearLayoutManager(activity)
        list = doctorRegisterDao.getall() as MutableList<DoctorRegisterTable>
        Log.e(TAG,"insertdataaaa " + doctorRegisterDao.getall().size)
        setAdapter(list as ArrayList<DoctorRegisterTable>)



        fab.setOnClickListener {

            setfragment(DoctorRegisterFragment())
        }

    }

    //set adapter
    private fun setAdapter(list: ArrayList<DoctorRegisterTable>) {

        if (list.size > 0) {
            doctorRegisterAdapter = DoctorRegisterAdapter(list,activity!!,this)
            recycleview.adapter = doctorRegisterAdapter
        } else {

        }
    }

    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickButton(position: Int, list: DoctorRegisterTable) {

        val builder = AlertDialog.Builder(CommonMethods.context)
        builder.setMessage("Are you sure want to Delete?")
        builder.setPositiveButton("Yes") { dialog, id ->
            doctorRegisterDao.delete(list.id!!)
            doctorRegisterAdapter.removeItem(position)
        }
        builder.setNegativeButton("No") { dialog, id -> dialog.dismiss() }
        builder.create().show()


    }
}
