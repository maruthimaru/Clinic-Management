package com.example.medicalmanagement.activity.admin
import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.helper.BitmapUtility
import com.example.medicalmanagement.helper.CommonMethods
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException


class DoctorRegisterFragment : Fragment() {
    private val TAG: String=DoctorRegisterFragment::class.java.simpleName
    internal var list=ArrayList<DoctorRegisterTable>()
    private var phtobitmap: Bitmap?=null
    lateinit var companyimage: ImageView
    internal lateinit var companyphotoeditbtn: ImageButton
    lateinit var doctorname:EditText
    lateinit var doctornumber:EditText
    lateinit var submit_btn: Button
    lateinit var doctoremail:EditText
    lateinit var specialist:EditText
    lateinit var doctortime:EditText
    lateinit var password:EditText
    lateinit var appDatabase: AppDatabase
    lateinit var doctorRegisterDao: DoctorRegisterDao
    lateinit var bitmapUtility:BitmapUtility
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_doctor_register_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        companyimage=view.findViewById(R.id.companyImg)
        companyphotoeditbtn=view.findViewById(R.id.imageButton2)
        doctorname=view.findViewById(R.id.doctorname)
        submit_btn = view.findViewById(R.id.submit_btn)
        doctornumber=view.findViewById(R.id.doctornumber)
        doctoremail=view.findViewById(R.id.doctoremail)
        specialist=view.findViewById(R.id.specialist)
        doctortime=view.findViewById(R.id.doctortime)
        password=view.findViewById(R.id.password)
        appDatabase = AppDatabase.getDatabase(activity!!)
        bitmapUtility= BitmapUtility(activity!!)
        commonMethods=CommonMethods(activity!!)
        doctorRegisterDao=appDatabase.doctorregisterdao()

        submit_btn.setOnClickListener { askAppointment() }

        companyphotoeditbtn.setOnClickListener {
            // setup the alert builder
            val builder = android.app.AlertDialog.Builder(activity)
            builder.setTitle("Choose an photo")
            // add a list
            val photo = arrayOf("Take Photo", "Select Photo", "Cancel")
            builder.setItems(photo) { dialog, which ->
                when (which) {
                    0 // Take Photo
                    -> {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, 0)//zero can be replaced with any action code
                        Log.e("TAG", "Click event for photo=$which")
                    }
                    1 // Select Photo
                    -> {
                        val pickPhoto = Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 1)//one can be replaced with any action code
                        Log.e("TAG", "Click event for photo=$which")
                    }

                    2 // Cancel
                    -> builder.setCancelable(true)
                }
            }
            // create and show the alert dialog
            val dialog = builder.create()
            dialog.show()

        }
    }
    private fun askAppointment(){
        val Doctorname=doctorname.text.toString()
        val Doctornumber=doctornumber.text.toString()
        val Doctoremail=doctoremail.text.toString()
        val Special=specialist.text.toString()
        val Doctortime=doctortime.text.toString()
        val Pass=password.text.toString()
        var logo = ""

        if (Doctorname.isNullOrEmpty()){
            doctorname.requestFocus()
            doctorname.error = "Please enter the doctor name"
        }
        else if (Doctornumber.isNullOrEmpty()){
            doctornumber.requestFocus()
            doctornumber.error = "Please enter the doctor number"
        }
        else if (Doctoremail.isNullOrEmpty()){
            doctoremail.requestFocus()
            doctoremail.error = "Please enter the doctor email"
        }
        else if (Special.isNullOrEmpty()){
            specialist.requestFocus()
            specialist.error = "Please enter the specialist"
        }
        else if (Doctortime.isNullOrEmpty()){
            doctortime.requestFocus()
            doctortime.error = "Please enter the doctor time"
        }
        else if (Pass.isNullOrEmpty()){
            password.requestFocus()
            password.error = "Please enter the password"
        }else{
            if (phtobitmap != null) {
                logo = bitmapUtility.getStringImage(phtobitmap!!)
            } else {
                logo = commonMethods.getBaseImage(commonMethods.getBytes((companyimage.drawable as BitmapDrawable).bitmap))
            }

            list.add(DoctorRegisterTable(Doctorname,Doctornumber,logo,Doctoremail,Special,Doctortime,Pass))
            Log.e("TAG", " doctorregister  " + list.size)
            Toast.makeText(activity!!,"Register successfully",Toast.LENGTH_SHORT).show()
            doctorRegisterDao.insert(list)
            setfragment(DoctorFragment())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 0) {

                    val bp = data!!.extras!!.get("data") as Bitmap
                    val resized = Bitmap.createScaledBitmap(bp, 100, 100, true)
                    phtobitmap=resized
                    val conv_bm = getRoundedRectBitmap(resized, 100)
                    companyimage.setImageBitmap(null)
                    companyimage.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                    //customerImg=getBytes(conv_bm);
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
            1 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 1) {


                    var bm: Bitmap? = null
                    if (data != null) {
                        try {
                            bm = MediaStore.Images.Media.getBitmap(context!!.contentResolver, data.data)
                            //                            int size=Math.min(bm.getWidth(),bm.getHeight());
                            //                            int x=(bm.getWidth()-size)/2;
                            //                            int y=(bm.getHeight()-size)/2;
                            //                            Bitmap bitmap=Bitmap.createBitmap(bm,x,y,size,size);
                            //                            Log.e("bitmap_Resul "," = "+ bitmap);

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }
                    val resized = Bitmap.createScaledBitmap(bm!!, 100, 100, true)
                    val conv_bm = getRoundedRectBitmap(resized, 100)
                    companyimage.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
        }
    }
    private fun getRoundedRectBitmap(bp: Bitmap, i: Int): Bitmap? {

        var result: Bitmap? = null
        try {
            result = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(result!!)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, 200, 200)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawCircle(50f, 50f, 50f, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bp, rect, rect, paint)

        } catch (e: NullPointerException) {
        } catch (o: OutOfMemoryError) {
        }
        return result
    }



    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
