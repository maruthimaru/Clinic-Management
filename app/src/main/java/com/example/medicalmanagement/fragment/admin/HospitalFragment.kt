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
import com.example.medicalmanagement.adapter.HospitalRegisterAdapter
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.HospitalRegisterDao
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.db.table.HospitalRegisterTable
import com.example.medicalmanagement.helper.CommonMethods
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HospitalFragment :Fragment(), HospitalRegisterAdapter.ListAdapterListener{

    var TAG = DoctorFragment::class.java.simpleName.toString()
    lateinit var fab: FloatingActionButton
    internal lateinit var recycleview: RecyclerView
    lateinit var list: MutableList<HospitalRegisterTable>
    lateinit var appDatabase: AppDatabase
    lateinit var hospitalRegisterDao: HospitalRegisterDao
    lateinit var hospitalRegisterAdapter: HospitalRegisterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_hospital_details, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        appDatabase = AppDatabase.getDatabase(activity!!)
        hospitalRegisterDao = appDatabase.hospitalregisterdao()
        list=ArrayList()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = LinearLayoutManager(activity)
        list = hospitalRegisterDao.getall() as MutableList<HospitalRegisterTable>
        Log.e(TAG,"insertdataaaa " + hospitalRegisterDao.getall().size)
        setAdapter(list as ArrayList<HospitalRegisterTable>)
        fab.setOnClickListener {

            setfragment(HospitalRegisterFragment())
        }
    }

    //set adapter
    private fun setAdapter(list: ArrayList<HospitalRegisterTable>) {

        if (list.size > 0) {
            hospitalRegisterAdapter = HospitalRegisterAdapter(list,activity!!,this)
            recycleview.adapter = hospitalRegisterAdapter
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

    override fun onClickButton(position: Int, list: HospitalRegisterTable) {

        val builder = AlertDialog.Builder(CommonMethods.context)
        builder.setMessage("Are you sure want to Delete?")
        builder.setPositiveButton("Yes") { dialog, id ->
            hospitalRegisterDao.delete(list.id!!)
            hospitalRegisterAdapter.removeItem(position)
        }
        builder.setNegativeButton("No") { dialog, id -> dialog.dismiss() }
        builder.create().show()


    }
}