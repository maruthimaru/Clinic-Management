package com.example.medicalmanagement.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.medicalmanagement.R
import com.example.medicalmanagement.helper.pojo.ColorsModel

import kotlin.collections.ArrayList

 class StaticData(internal var context: Context) {

    init {
    }

     //Color List for User Image Dynamic change
     val colorList: List<ColorsModel>
         get() {
             val allItems = ArrayList<ColorsModel>()
             allItems.add(ColorsModel("A", R.color.colorA))
             allItems.add(ColorsModel("B", R.color.colorB))
             allItems.add(ColorsModel("C", R.color.colorC))
             allItems.add(ColorsModel("D", R.color.colorD))
             allItems.add(ColorsModel("E", R.color.colorE))
             allItems.add(ColorsModel("F", R.color.colorF))
             allItems.add(ColorsModel("G", R.color.colorG))
             allItems.add(ColorsModel("H", R.color.colorH))
             allItems.add(ColorsModel("I", R.color.colorI))
             allItems.add(ColorsModel("J", R.color.colorJ))
             allItems.add(ColorsModel("K", R.color.colorK))
             allItems.add(ColorsModel("L", R.color.colorL))
             allItems.add(ColorsModel("M", R.color.colorM))
             allItems.add(ColorsModel("N", R.color.colorN))
             allItems.add(ColorsModel("O", R.color.colorO))
             allItems.add(ColorsModel("P", R.color.colorP))
             allItems.add(ColorsModel("Q", R.color.colorQ))
             allItems.add(ColorsModel("R", R.color.colorR))
             allItems.add(ColorsModel("S", R.color.colorS))
             allItems.add(ColorsModel("T", R.color.colorT))
             allItems.add(ColorsModel("U", R.color.colorU))
             allItems.add(ColorsModel("V", R.color.colorV))
             allItems.add(ColorsModel("W", R.color.colorW))
             allItems.add(ColorsModel("X", R.color.colorX))
             allItems.add(ColorsModel("Y", R.color.colorY))
             allItems.add(ColorsModel("Z", R.color.colorZ))
             return allItems
         }


fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    var drawable = ContextCompat.getDrawable(context, drawableId)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable!!).mutate()
    }

    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

    fun staticSpinnerData(visitto: ArrayList<String>, visittoid: ArrayList<String>, meetto: ArrayList<String>, meettoid: ArrayList<String>) {
        //select purpose
        visitto.add("Select Purpose")
        visittoid.add("0")
        visitto.add("Official")
        visittoid.add("1")
        visitto.add("Interview")
        visittoid.add("2")
        //Select persion
        meetto.add("Select Person")
        meettoid.add("0")
        meetto.add(" Ragu( Manager )")
        meettoid.add("1")
        meetto.add(" SriRam( HR )")
        meettoid.add("2")
    }

    fun SpinnerList(List: ArrayList<String>) {
        List.add("Recent")
        List.add("Inward")
        List.add("Outward")
    }

    fun SpinnerCancelList(List: ArrayList<String>) {
        List.add("Upcoming")
        List.add("Cancel")
    }

    fun userTypeList(List: ArrayList<String>) {
        List.add("Choose user type")
        List.add("Admin")
//        List.add("Employee")
        List.add("Gate")
        List.add("Self")

    }

    fun unitList(List: ArrayList<String>) {
        List.add("choose unit")
        List.add("UNIT - 1")
        List.add("UNIT - 2")
        List.add("UNIT - 3")
        List.add("UNIT - 4")

    }

    fun gateList(List: ArrayList<String>) {
        List.add("choose gate")
        List.add("GATE - 1")
        List.add("GATE - 2")
        List.add("GATE - 3")
        List.add("GATE - 4")
    }

     fun getGatePassList():ArrayList<String>{
         var List = ArrayList<String>()
         List.add("Returnable")
         List.add("Non Returnable")
         return List
     }

     fun getDurationList():ArrayList<String>{
         val categories = ArrayList<String>()
         categories.add(0,"Current Date")
         categories.add(1,"This Month")
         categories.add(2,"Between")
         return categories
     }
    fun statusList(List: ArrayList<String>) {
        List.add("Active")
        List.add("In-Active")
    }
     //get the Input (User/Customer/Product/CompanyName) and Return the name and color for the respective input fields
     fun getSelectedName(input: String): List<ColorsModel> {
         val colorDataSourceList = colorList
         val selectedList = ArrayList<ColorsModel>()
         for (dataSource in colorDataSourceList) {
             if (dataSource.name == input.toUpperCase()) {
                 selectedList.add(ColorsModel(dataSource.name, dataSource.color))
             }
         }
         return selectedList
     }



}