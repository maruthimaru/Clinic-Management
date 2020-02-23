package com.example.medicalmanagement.fragment.doctor

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.activity.AdminMainActivity
import com.example.medicalmanagement.activity.DoctorMainActivity
import com.example.medicalmanagement.activity.PatientMainActivity
import com.example.medicalmanagement.activity.PatientRegisterActivity
import com.example.medicalmanagement.adapter.AdminHomeAdapter
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.db.table.PatientRegisterTable
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.Constants
import com.example.medicalmanagement.helper.pojo.AdminHomeModel
import java.util.ArrayList

class DoctorHomeFragment : Fragment(),AdminHomeAdapter.ListAdapterListener {


    private val TAG: String=DoctorHomeFragment::class.java.simpleName
    private lateinit var doctorDetails: DoctorRegisterTable
    internal lateinit var view: View
    internal lateinit var list: MutableList<AdminHomeModel>
    internal lateinit var homeAdapter: AdminHomeAdapter
    internal lateinit var recyclerView: RecyclerView
    lateinit var commonMethods: CommonMethods
    private var lLayout: GridLayoutManager? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_admin_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        recyclerView = view.findViewById(R.id.recycler)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        commonMethods= CommonMethods(activity!!)
//        list.add(AdminHomeModel(R.drawable.ic_logo, getString(R.string.mydetails)))
//        list.add(AdminHomeModel(R.drawable.ic_book_appiontment, getString(R.string.book_appiontment)))
//        list.add(AdminHomeModel(R.drawable.ic_viewbook, getString(R.string.view_book)))
//        list.add(AdminHomeModel(R.drawable.ic_cancel_book, getString(R.string.cancel_book)))
        list.add(AdminHomeModel(R.drawable.ic_search_doctor, getString(R.string.profile)))
        list.add(AdminHomeModel(R.drawable.ic_feedback, getString(R.string.appointment)))
        list.add(AdminHomeModel(R.drawable.ic_logout, getString(R.string.logout)))


        lLayout = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = lLayout
        homeAdapter = AdminHomeAdapter(list, activity!!, this@DoctorHomeFragment)
        recyclerView.adapter = homeAdapter
        val activity = activity as DoctorMainActivity?

        var actionBar: ActionBar? = null
        if (activity != null) {
            actionBar = activity.supportActionBar
        }
        if (actionBar != null) {
            actionBar.title = resources.getString(R.string.home)

        }

        var bundle =arguments
        if (bundle!=null) {
            doctorDetails= bundle.getSerializable(Constants.doctorList) as DoctorRegisterTable
        }

    }
    override fun onClickButton(position: Int) {
        Log.e(ContentValues.TAG, "onClickButton: $position")
        when (position) {

            0 -> {
            setfragment(DoctorProfileFragment(),doctorDetails)
            }
            1 -> {
//                Toast.makeText(activity!!,"Add Appointment" ,Toast.LENGTH_SHORT).show()
                Log.e(TAG,"doctorDetails + ${doctorDetails.name}")
                setfragment(DoctorAppointmentListFragment(),doctorDetails)
            }
            2 -> {
                commonMethods.Logoutscreen()
            }
            3 -> {
            }
            4 -> {

            }
            5 -> {
            }
            6 -> {

            }
        }


    }

    private fun setfragment(_fragment: Fragment,doctorList:DoctorRegisterTable) {
        var bundle =Bundle()
        bundle.putSerializable(Constants.doctorList,doctorList)
        val fm = activity!!.supportFragmentManager
        _fragment.arguments=bundle
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
