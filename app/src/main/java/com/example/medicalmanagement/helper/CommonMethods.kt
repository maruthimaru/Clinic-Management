package com.example.medicalmanagement.helper

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.nfc.Tag
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.medicalmanagement.R
import com.example.medicalmanagement.activity.LoginActivity
import com.example.medicalmanagement.helper.pojo.NavDrawerItem
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonMethods{
    private val TAG=CommonMethods::class.java.simpleName
    private lateinit var dateListener: DateListener
    private var picker: DatePickerDialog? = null

    constructor(context: Context){
        CommonMethods.context = context    }

    /* Context is converted to Activity.*/
    fun convertActivity(context: Context): Activity {
        return context as Activity
    }
    //convert byte[] to base64
    fun getBaseImage(image: ByteArray): String {
        return Base64.encodeToString(image,
                Base64.NO_WRAP)

    }
    fun getdate(time: String, input: String, outTime: String): String? {

        var inputFormat = SimpleDateFormat(input)
        val outputFormat = SimpleDateFormat(outTime)
        var date: Date? = null
        var str: String? = null
        var finalDate: Date? = null
        try {
            date = inputFormat.parse(time)
            val calendar = Calendar.getInstance()
            calendar.time = date

            finalDate = Date(calendar.timeInMillis)
            str = outputFormat.format(finalDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            inputFormat = SimpleDateFormat(outTime)
            try {
                date = inputFormat.parse(time)
                val calendar = Calendar.getInstance()
                calendar.time = date
                finalDate = Date(calendar.timeInMillis)
                str = outputFormat.format(finalDate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }
        }

        return str
    }

    // convert from bitmap to byte array
    @Throws(Exception::class)
    fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }


    constructor(dateListener: DateListener, context: Context) {
        CommonMethods.context = context
        this.dateListener = dateListener
    }

    //navigation drawer list items:
    fun getNavigationList(): List<NavDrawerItem> {

        val allItems = ArrayList<NavDrawerItem>()
        allItems.add(NavDrawerItem("Home", R.drawable.sidemenu_home))
//        allItems.add(NavDrawerItem("Employee profile", R.drawable.menu_profile))
        allItems.add(NavDrawerItem("Logout", R.drawable.sidemenu_logout))
        return allItems
    }

    fun showConfirmation() {
        AlertDialog.Builder(context).setTitle(context.getString(R.string.confirmation))
                .setMessage(context.getString(R.string.confirmation_message))
                .setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                    val activity = context as Activity
                    activity.finish()
                }
                .setNegativeButton(context.getString(R.string.no)) { dialogInterface, i ->
                    dialogInterface.dismiss()
                }.show()
    }


    @Throws(NullPointerException::class)
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    fun intentFinish(_context: Context, _targetClass: Class<*>) {
        val activity = _context as Activity
        val intent = Intent(_context, _targetClass)
        activity.startActivity(intent)
        activity.finish()
    }


    fun hideKeyboard() {
        //Convert Context to Activity
        val activity = convertActivity(context)
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull<View>(activity.getCurrentFocus()).windowToken, 0)

    }

    companion object {
        internal lateinit var context: Context
        private var Date: String? = null
    }

    fun Logoutscreen() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure want to logout?")
        builder.setPositiveButton("Yes") { dialog, id ->
            intentFinish(context, LoginActivity::class.java)
        }
        builder.setNegativeButton("No") { dialog, id -> dialog.dismiss() }
        builder.create().show()
    }

}
