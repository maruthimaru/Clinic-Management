package com.example.medicalmanagement.fragment.admin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.activity.AdminMainActivity
import com.example.medicalmanagement.adapter.AdminHomeAdapter
import com.example.medicalmanagement.fragment.admin.chart.HalfPieChartActivity
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.pojo.AdminHomeModel
import java.util.ArrayList

class AdminHomeFragment : Fragment(),AdminHomeAdapter.ListAdapterListener {


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
//        list.add(AdminHomeModel(R.drawable.ic_viewbook, getString(R.string.view_book)))
        list.add(AdminHomeModel(R.drawable.ic_feedback, getString(R.string.add_hospital)))
        list.add(AdminHomeModel(R.drawable.ic_feedback, getString(R.string.timimg)))
        list.add(AdminHomeModel(R.drawable.ic_search_doctor, getString(R.string.add_doctor)))
        list.add(AdminHomeModel(R.drawable.ic_book_appiontment, getString(R.string.appointment)))
        list.add(AdminHomeModel(R.drawable.graph, getString(R.string.graph)))
        list.add(AdminHomeModel(R.drawable.ic_logout, getString(R.string.logout)))


        lLayout = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = lLayout
        homeAdapter = AdminHomeAdapter(list, activity!!, this@AdminHomeFragment)
        recyclerView.adapter = homeAdapter
        val activity = activity as AdminMainActivity?

        var actionBar: ActionBar? = null
        if (activity != null) {
            actionBar = activity.supportActionBar
        }
        if (actionBar != null) {
            actionBar.title = resources.getString(R.string.home)

        }

    }
    override fun onClickButton(position: Int) {
        Log.e(ContentValues.TAG, "onClickButton: $position")
        when (position) {
            0 -> {
                setfragment(HospitalFragment())
            }
            1 -> {
                setfragment(ScheduleTimeFragment())
            }
            2 -> {
                setfragment(DoctorFragment())
            }
            3 -> {
                setfragment(AllAppointmentListFragment())
            }
            4 -> {
//                setfragment(Graph())
                startActivity(Intent(activity!!,HalfPieChartActivity::class.java))
            }
            5 -> {
                commonMethods.Logoutscreen()
            }

        }


    }

    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
