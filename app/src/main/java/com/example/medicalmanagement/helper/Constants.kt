package com.example.medicalmanagement.helper


import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import java.util.ArrayList

/**
 * Created by admin on 16-Feb-18.
 */

class Constants {
    fun killAllToast() {
        //        Log.e(TAG, "killAllToast: size : "+msjsToast.size() );
        if (msjsToast != null) {
            for (t in msjsToast!!) {
                t.cancel()
            }
            msjsToast!!.clear()
        }
    }

    companion object {


        val doctorList: String="DoctorDetails"
        val patientList: String="PatientDetails"
        val transport: String="transport"
        val in_person: String="in_person"
        val driverDetails: String = "driver_details"
        val vehicleMasters: String = "vehicle_masters"
        val courierCompany: String = "courier_company"
        val materialType: String = "material_type"
        val vehicleType: String = "vehicle_type"
        val uom: String = "uom"
        val supplier: String = "supplier"
        val employeeMasters: String = "employee_masters"
        val prefixSetting: String = "prefix_setting"
        val companySetting: String = "company_setting"
        val emailSetting: String = "email_setting"
        val material: String = "material"
        val vehicle: String = "vehicle"
        const val vehicleId: String="vehicleId"
        val insertUpdate: String="insertUpdate"
        val checkoutCount: String = "checkoutCount"
        const val report = "Report"
        const val settings = "Settings"
        const val courier = "Courier"
        const val iom = "Iom"
        const val empList = "EmpList"
        val all: String="All"
        val inward: String="INWARD"
        val active: String="Active"
        val outward: String="OUTWARD"
        val own: String="Own"
        val private: String="Private"
        val statusin: String="IN"
        val statusout: String="OUT"
        val name: String = "name"
        val number: String = "number"
        val visitor: String="visitor"
        val vIn: String="in"
        val vOut: String="out"
        val ein:String="Inward"
        val eout:String="Outward"
        var yes = "Yes"
        var no = "No"
      const  val dateformat1="dd-MM-yyyy"
        val timeformat12="hh:mm a"
        val dateformat2="HH:mm"
        val Company:String="companyid"
        val CompanyLogo:String="logo"
        val CompanyName:String="name"
        val Employeeid:String="employeeid"
        val checkin:String="CheckIn"
        val checkout:String="CheckOut"
        var personORtrans = ""
        val checkinstatus:Boolean=false

        internal var TAG = Constants::class.java.simpleName

        val errorNetwork = "Check your Internet Connection"
        val customer = "Customer"
        val pinNo = "pinNo"
        val admin = "admin"
        val gate = "gate"
        val gatepass = "gatepass"
        val employee = "employee"
        val employee_in = "employee_in"
        val employee_out = "employee_out"
        /* Toast Error Message*/
        val e = "No Details Found Please Enter Manually."
        val login = "login"
        val orders = "orders"
        val screenName = "screen"
        val userId = "userId"
        val randomNo = "randomNo"
        val position = "position"
        val arrayList = "arrayList"
        val edit = "edit"
        val add = "add"
        val userid = "userid"
        val deviceID = "deviceID"
        var action = "add"
        val share = "share"
        val help = "help"
        val details = "details"
        val deviceAddress = "deviceAddress"
        val deviceStatus = "deviceStatus"
        val change = "change"
        val back = "back"
        val list = "list"
        val uart = "uart"
        val keyShare = "keyShare"
         const val  home = "home"
        val memberDetail = "memberDetail"
        val accessLog = "accessLog"
        val profile = "profile"
        const val logout = "logout"
        const val manageuser = "Manageuser"
        const val masters = "Masters"
        val setting = "setting"
        val nullValue = "NULL"
        val contactNo = "1234567890"
        const  val baseURL = "baseURL"
        val primary = "p"
        val family = "Family"
        val shared_list = "Shared"
        val new_share = "New_Share"
        val validDate = "31-12-2018"
        val guestValidDate = "14-11-2018"
        val guest = "Guest"
        val relationsipFamily = "family"
        val relationsipGuest = "guest"
        val memberId = "memberId"
        var count = 0

        val success = "success"
        val fail = "fail"
        val connection = "connection"
        val mobileNo = "mobileNo"
        const val searchTypeCount="searchTypeCount"
        const val RadioBtnCount="RadioBtnCount"
        val userType = "type"
        val update = "update"
        val title = "title"
        var pushNotifyStatus = false
        var cancel_scan = "cancel"
        var data = "data"
        var msjsToast: ArrayList<Toast>? = ArrayList()
        var idleCheck: ArrayList<Toast>? = ArrayList()
        var connectionDialog: ArrayList<AlertDialog>? = ArrayList()


        val VehicleName="two"
        val VehicleNo="one"
        val ScreenType=""
        var View = "view"
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val param = "key"
        val pending = "00:00"
        val time = "00:00"
        val timeSeconds = "00:00:00"

        var Vehicle_PassNo = "Vehicle_PassNo"
        var delete = "delete"
        var click = "click"
       var status = "status"
        var searchBy = "name"
        var cancel = "cancel"
        var upcoming = "upcoming"
        var companyId = "companyId"
        var bluetoothAddress = "34:81:F4:27:28:76"
        var adminID = "1"
        var nonReturn = "nonReturnable"
        var returnable = "returnable"
        //date formats
        var yyyymmdd="yyyy-MM-dd"
        var ddmmyyyy="dd-MM-yyyy"
        var ddmyyyy="dd/M/yyyy"
        //time formats
        var hhmma="hh:mm a"
        var HHMM="HH:mm"
        var hhmm="hh:mm"
        var HHMMSS="HH:mm:ss"
        var hhmmaa="hh:mm aa"
        var hhmmss="hh:mm:ss"
       // date and time formats
        var ddmmyyyy_HHmm="dd-MM-yyyy HH:mm"
        var ddmmyyyy_hhmm="dd-MM-yyyy hh:mm"
        var yyyymmdd_HHmmss="yyyy-MM-dd HH:mm:ss"
        var yyyymmdd_hhmm_a="yyyy-MM-dd hh:mm a"
        var ddMyyyy_hhmmss="dd-M-yyyy hh:mm:ss"
        var backPressCount=0
        var delete_id:String=""

        fun killAllDialog() {
            if (connectionDialog != null) {
                for (t in connectionDialog!!) {
                    if (t != null) {
                        t.cancel()
                        t.dismiss()
                    }
                }
                connectionDialog!!.clear()
            }
        }

        fun killIdleToast() {
            if (idleCheck != null && idleCheck!!.size > 0) {
                for (t in idleCheck!!) {
                    t.cancel()
                }
                idleCheck!!.clear()
            }
        }


    }


}
