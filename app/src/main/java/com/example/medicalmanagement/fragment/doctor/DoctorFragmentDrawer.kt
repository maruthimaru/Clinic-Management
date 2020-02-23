package com.example.medicalmanagement.fragment.doctor

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.Constants
import com.example.medicalmanagement.helper.NavigationDrawerAdapter
import com.example.medicalmanagement.helper.RecyclerTouchListener
import com.example.medicalmanagement.helper.pojo.NavDrawerItem

class DoctorFragmentDrawer : Fragment() {


    private lateinit var doctorDetails: DoctorRegisterTable
    val TAG= DoctorFragmentDrawer::class.java.simpleName
    lateinit var recyclerView:RecyclerView
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private lateinit var containerView: View
    lateinit var commonClass: CommonMethods
    private var drawerListener: FragmentDrawerListener? = null
    lateinit var imageViewStudentImg:ImageView
    lateinit var textViewStudentName:TextView
    lateinit var textViewRollNo:TextView
    lateinit var textViewStagging:TextView
    lateinit var appDatabase: AppDatabase
    lateinit var doctorRegisterDao: DoctorRegisterDao


   lateinit var rootView: View

    interface FragmentDrawerListener {
        fun onDrawerItemSelected(view: View, position: Int)
        fun onDrawerItemView(view: View)
    }

    fun setDrawerListener(listener: FragmentDrawerListener) {
        this.drawerListener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_drawer,container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.drawerList)
        imageViewStudentImg=view.findViewById(R.id.imageViewStudentImg)
        textViewStudentName=view.findViewById(R.id.textViewStudentName)
        textViewRollNo=view.findViewById(R.id.textViewRollNo)
        textViewStagging=view.findViewById(R.id.textViewStagging)
        commonClass= CommonMethods(activity!!)
        appDatabase = AppDatabase.getDatabase(activity!!)
        doctorRegisterDao=appDatabase.doctorregisterdao()

        val list:List<NavDrawerItem> = commonClass.getNavigationList()


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(context)
        if (list.size>0){
            val navigationDrawerAdapter= NavigationDrawerAdapter(activity!!,list)
            recyclerView.adapter = navigationDrawerAdapter
        }

        recyclerView.addOnItemTouchListener(
                RecyclerTouchListener(
                        activity!!,
                        recyclerView,
                        object : RecyclerTouchListener.ClickListener {
                            override fun onClick(view: View, i: Int) {
                                Log.e("onClick"," position "+i)
                                drawerListener!!.onDrawerItemSelected(view,i)
                                mDrawerLayout!!.closeDrawer(containerView)
                                when(i){
                                    0->{
                                        var doctorDetails=doctorRegisterDao.login(doctorDetails.email!!,doctorDetails.password!!)
                                        setfragment(DoctorHomeFragment(),doctorDetails)
                                    }
                                    1->{
                                        commonClass.Logoutscreen()
                                    }
                                }
                            }
                            override fun onLongClick(view: View?, i: Int) {

                            }
                        })
        )
    }

    private fun setfragment(_fragment: Fragment,doctorList: DoctorRegisterTable) {
        var bundle =Bundle()
        bundle.putSerializable(Constants.doctorList,doctorList)
        val fm = activity!!.supportFragmentManager
        _fragment.arguments=bundle
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setfragment(_fragment: Fragment) {
        val fm = activity!!.supportFragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    fun setUp(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar,doctorDetails:DoctorRegisterTable) {
        this.doctorDetails=doctorDetails
        containerView = activity!!.findViewById(fragmentId)
        mDrawerLayout = drawerLayout
        mDrawerToggle = object :
                ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                //if Home Button pressed hide the soft input
//                commonClass.hideKeyboard()
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                //if Home Button pressed hide the soft input
//                commonClass.hideKeyboard()
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        mDrawerLayout!!.setDrawerListener(mDrawerToggle)
        toolbar.setNavigationIcon(R.drawable.ic_menunew)
        mDrawerToggle!!.setHomeAsUpIndicator(R.drawable.ic_menunew)
        mDrawerLayout!!.post{ (mDrawerToggle as ActionBarDrawerToggle).syncState() }

        toolbar.setNavigationOnClickListener {
            mDrawerLayout!!.openDrawer(Gravity.START)
            //if Home Button pressed hide the soft input
//            commonClass.hideKeyboard()
            activity!!.invalidateOptionsMenu()
        }

    }

}