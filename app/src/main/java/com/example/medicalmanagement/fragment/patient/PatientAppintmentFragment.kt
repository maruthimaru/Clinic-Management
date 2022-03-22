package com.example.medicalmanagement.fragment.patient

import android.R.attr
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.medicalmanagement.R
import com.example.medicalmanagement.adapter.*
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.*
import com.example.medicalmanagement.db.table.*
import com.example.medicalmanagement.helper.BitmapUtility
import com.example.medicalmanagement.helper.CommonMethods
import com.example.medicalmanagement.helper.Constants
import com.example.medicalmanagement.helper.RecyclerTouchListener
import com.example.medicalmanagement.helper.adapter.UploadimageAdapter
import com.example.medicalmanagement.helper.pojo.ImagesModel
import droidninja.filepicker.FilePickerConst
import droidninja.filepicker.FilePickerConst.KEY_SELECTED_DOCS
import id.zelory.compressor.Compressor
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PatientAppintmentFragment : Fragment(), DoctorSpecialistAdapter.ItemSelectedLisitner, DoctorNameAdapter.ItemSelectedLisitner, SchedulePatienttimeAdapter.ListAdapterListener {
    private lateinit var selectedNamelist: DoctorRegisterTable
    private lateinit var scheduletimeAdapter: SchedulePatienttimeAdapter
    private lateinit var timeList: java.util.ArrayList<ScheduleTime>
    private lateinit var uploadimageAdapter: UploadimageAdapter
    private lateinit var patientDetails: PatientRegisterTable
    private val TAG: String = PatientAppintmentFragment::class.java.simpleName
    internal var list = ArrayList<PatientRegisterTable>()
    private var phtobitmap: Bitmap? = null
    lateinit var companyimage: ImageView
    internal lateinit var companyphotoeditbtn: ImageButton
    lateinit var doctorname: AutoCompleteTextView
    lateinit var doctornumber: TextView
    lateinit var submit_btn: Button
    lateinit var search_btn: Button
    lateinit var doctoremail: TextView
    lateinit var specialist: EditText
    lateinit var select_hospital:Spinner
    lateinit var password: AutoCompleteTextView
    lateinit var appDatabase: AppDatabase
    lateinit var patientRegisterDao: PatientRegisterDao
    lateinit var patientAppointmentDao: PatientAppointmentDao
    lateinit var doctorRegisterDao: DoctorRegisterDao
    lateinit var scheduleTimeDao: ScheduleTimeDao
    lateinit var hospitalRegisterDao: HospitalRegisterDao
    lateinit var bitmapUtility: BitmapUtility
    internal lateinit var commonMethods: CommonMethods
    val requestcode = 3

    private val imgList = ArrayList<ImagesModel>()
    private val imagelist = ArrayList<String>()
    private val imagepath = ArrayList<String>()
    private var mCurrentPhotoPath: String? = null
    private val Document = 18
    private var actualImage: File? = null
    lateinit var docpic: TextView
    lateinit var recyclerView_doc_img: RecyclerView
    private var compressedImage: File? = null
    var selectedItem = ""
    lateinit var hospital_list: MutableList<HospitalRegisterTable>

    var docPaths = ArrayList<String>()
    internal lateinit var recycleview: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_patient_appontment_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        companyimage = view.findViewById(R.id.companyImg)
        companyphotoeditbtn = view.findViewById(R.id.imageButton2)
        doctorname = view.findViewById(R.id.doctorname)
        submit_btn = view.findViewById(R.id.submit_btn)
        search_btn = view.findViewById(R.id.search_btn)
        doctornumber = view.findViewById(R.id.doctornumber)
        doctoremail = view.findViewById(R.id.doctoremail)
        specialist = view.findViewById(R.id.specialist)
        password = view.findViewById(R.id.password)
        select_hospital = view.findViewById(R.id.selectHospital)
        recycleview = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView_doc_img = view.findViewById(R.id.recyclerView_doc_img)
        docpic = view.findViewById(R.id.docpic)
        appDatabase = AppDatabase.getDatabase(activity!!)
        bitmapUtility = BitmapUtility(activity!!)
        commonMethods = CommonMethods(activity!!)
        patientRegisterDao = appDatabase.patientRegisterDao()
        doctorRegisterDao = appDatabase.doctorregisterdao()
        scheduleTimeDao = appDatabase.schudleTimeDao()
        hospitalRegisterDao = appDatabase.hospitalregisterdao()
        patientAppointmentDao = appDatabase.patientAppointmentDao()
        recycleview.setHasFixedSize(true)
        recycleview.layoutManager = GridLayoutManager(activity, 4)

        doctornumber.setText(commonMethods.getdate(Constants.dateformat1))
        doctornumber.setOnClickListener {
            commonMethods.clickDate(doctornumber)
        }
//        var doctorList = doctorRegisterDao.getSpecialist() as ArrayList
//        val hashSet = HashSet(doctorList);
//        Log.e(TAG, " hasset " + hashSet.size)
//        doctorList.clear()
//        doctorList = ArrayList<String>(hashSet)
//        val specialistAdapter = DoctorSpecialistAdapter(activity!!, 0, 0, doctorList, this)
//        password.setAdapter(specialistAdapter)
//        password.threshold = 1
        var bundle = arguments
        if (bundle != null) {
            patientDetails = bundle.getSerializable(Constants.patientList) as PatientRegisterTable
//            var image= Base64.decode(patientDetails.image, Base64.DEFAULT);
//            commonMethods.loadImage(image,companyimage)
        }
        hospital_list = hospitalRegisterDao.getall() as MutableList<HospitalRegisterTable>
        var spinnerAdapter= HospitalSpinnerAdapter(activity!!, android.R.layout.simple_spinner_item,hospital_list)
        select_hospital.adapter=spinnerAdapter

        select_hospital.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedItem = hospital_list[p2].id.toString()
                Log.e(TAG, "onItemSelected: "+hospital_list[p2].id )
                var doctorList = doctorRegisterDao.getSpecialist(selectedItem) as ArrayList
                val hashSet = HashSet(doctorList);
                Log.e(TAG, " hasset " + hashSet.size)
                doctorList.clear()
                doctorList = ArrayList<String>(hashSet)
                val specialistAdapter = DoctorSpecialistAdapter(activity!!, 0, 0, doctorList, this@PatientAppintmentFragment)
                password.setAdapter(specialistAdapter)
                password.threshold = 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        password.setOnItemClickListener { parent, view, position, id ->
            val selectedSpecialist = parent!!.adapter.getItem(position) as String
            Log.e(TAG, "specialist name : " + selectedSpecialist)
            val doctorSpecialistList = doctorRegisterDao.getSpecialistDoctor(selectedSpecialist!!,selectedItem)
            Log.e(TAG, "doctorSpecialistList " + doctorSpecialistList.size)
            val nameAdapter = DoctorNameAdapter(activity!!, 0, 0, doctorSpecialistList, this)
            doctorname.setAdapter(nameAdapter)
            doctorname.threshold = 1
        }
        timeList = ArrayList<ScheduleTime>()

        doctorname.setOnItemClickListener { parent, view, position, id ->
            selectedNamelist = parent!!.adapter.getItem(position) as DoctorRegisterTable

        }

        submit_btn.setOnClickListener { askAppointment() }

        search_btn.setOnClickListener {
            searchDoctor()
        }

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

        recyclerView_doc_img.setHasFixedSize(true)
        recyclerView_doc_img.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_doc_img.itemAnimator = DefaultItemAnimator()
        recyclerView_doc_img.addOnItemTouchListener(RecyclerTouchListener(activity!!, recyclerView_doc_img, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {}

            override fun onLongClick(view: View?, position: Int) {
                if (imagelist.isEmpty() || imagepath.isEmpty()) {
                    val t = Toast.makeText(activity, "You Can't delete this Photo!!!", Toast.LENGTH_SHORT)
                    t.setGravity(17, 0, 0)
                    t.show()
                    return
                }
                val builder = AlertDialog.Builder(activity)
                builder.setMessage(resources.getString(R.string.sureDelete))
                builder.setPositiveButton("OK") { dialog, id ->
                    imgList.removeAt(position)
                    imagelist.removeAt(position)
                    val path = imagepath.get(position)
                    val fdelete = File(path)
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            imagepath.removeAt(position)
                            uploadimageAdapter.notifyDataSetChanged()
                        } else {
                        }
                    }
                }
                builder.setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }
                builder.create().show()
            }
        }))

        docpic.setOnClickListener {
            //                imgmsg.setVisibility( View.VISIBLE );
            recyclerView_doc_img.visibility = View.VISIBLE
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            actualImage = createImageFile()
            if (actualImage != null) {
                val photoURI: Uri
                if (Build.VERSION.SDK_INT > 19) {
                    photoURI = FileProvider.getUriForFile(activity!!, "com.example.medicalmanagement.fileprovider", actualImage!!)
                } else {
                    photoURI = Uri.fromFile(actualImage)
                }
                intent.putExtra("output", photoURI)
                startActivityForResult(intent, Document)
            }
        }

    }

    //set adapter
    private fun setAdapter(list: ArrayList<ScheduleTime>) {

        if (list.size > 0) {
            scheduletimeAdapter = SchedulePatienttimeAdapter(list, activity!!, this)
            recycleview.adapter = scheduletimeAdapter
        }
    }

    private fun createImageFile(): File {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir()
        }
        val image = File(mediaStorageDir, "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + ".jpg")
        mCurrentPhotoPath = image.absolutePath
        return image
    }


    private fun askAppointment() {
        val Doctorname = doctorname.text.toString()
        val Doctornumber = doctornumber.text.toString()

        val Special = specialist.text.toString()
        val Pass = password.text.toString()
        var logo = ""

        val timing = scheduletimeAdapter.getClickedStatus()
        if (Doctorname.isNullOrEmpty()) {
            doctorname.requestFocus()
            doctorname.error = "Please enter the doctor name"
        } else if (Doctornumber.isNullOrEmpty()) {
            doctornumber.requestFocus()
            doctornumber.error = "Please enter the doctor number"
        } else if (Special.isNullOrEmpty()) {
            specialist.requestFocus()
            specialist.error = "Please enter the Symptoms"
        } else if (Pass.isNullOrEmpty()) {
            password.requestFocus()
            password.error = "Please enter the password"
        } else {
            val appointmentList = ArrayList<PatientAppointmentTable>()
            appointmentList.add(PatientAppointmentTable(patientDetails.name, imagelist, patientDetails.phone, patientDetails.age, patientDetails.email,
                selectedNamelist.id!!.toString(), selectedNamelist.name, selectedNamelist.specialist, Doctornumber, timing))
            commonMethods.sendSMS(selectedNamelist.phone,Special)
            Log.e("TAG", " doctorregister  " + list.size)
            Toast.makeText(activity!!, "Register successfully", Toast.LENGTH_SHORT).show()
            patientAppointmentDao.insert(appointmentList)
//            list = patientRegisterDao.getall() as MutableList<DoctorRegisterTable>

        }

    }

    private fun searchDoctor() {
        val Doctorname = doctorname.text.toString()
        val Doctornumber = doctornumber.text.toString()
//        val Doctoremail=doctoremail.text.toString()
        val Special = specialist.text.toString()
        val Pass = password.text.toString()
        var logo = ""

        if (Doctorname.isNullOrEmpty()) {
            doctorname.requestFocus()
            doctorname.error = "Please enter the doctor name"
        } else if (Doctornumber.isNullOrEmpty()) {
            doctornumber.requestFocus()
            doctornumber.error = "Please enter the doctor number"
        } else if (Pass.isNullOrEmpty()) {
            password.requestFocus()
            password.error = "Please enter the password"
        } else {
            val convetedTime = DoctorRegDataConversion().fromOptionValuesList((selectedNamelist.time as List<String>?)!!)
//            Log.e(TAG,"specialist time : " + selectedNamelist.id.toString() + "data: ${Doctornumber } time " +convetedTime)
            val searchList = patientAppointmentDao.getTime(selectedNamelist.id.toString(), Doctornumber)
            Log.e(TAG, "specialist name : " + searchList + "  searchList " + searchList)



            if (searchList.size <= 0) {
                timeList.clear()
                for (timeModel in selectedNamelist.time!!) {
                    timeList.add(ScheduleTime(timeModel!!))
                }
                setAdapter(timeList)
            } else {
                timeList.clear()

//                for (i in 0 until selectedNamelist.time!!.size) {
//                    timeList.add(ScheduleTime(selectedNamelist.time!![i]))
//                }
                val getconvetedTime=ArrayList<String>()
                for (split in searchList) {
                    getconvetedTime.addAll(DoctorRegDataConversion().toOptionValuesList(split) as ArrayList<String>)
                }

                for (timeModel in 0 until selectedNamelist.time!!.size) {
                    timeList.add(ScheduleTime(selectedNamelist.time!![timeModel]!!))
                    for (split in getconvetedTime) {
                        if (split.equals(selectedNamelist.time!![timeModel])){
                            Log.e(TAG, "getconvetedTime timeModel $timeModel split :" + split)
                            timeList[timeModel].clickStatus=2
                        }
                    }
                }


//                for (i in 0 until selectedNamelist.time!!.size) {
//                    timeList.add(ScheduleTime(selectedNamelist.time!![i]))
//                    for (timeModel in searchList){
//
////                        val str = selecModel.time!!.replaceAll("\\[", "").replaceAll("\\]","");
////                            Log.e(TAG, " selecModel.phone selecModel.time!! ${timeModel} and  selectedNamelist.time!![i] ${selectedNamelist.time!![i]}")
////                            if (selecModel.phone.equals(patientDetails.phone) && timeModel.equals(selectedNamelist.time!![i])) {
////                                timeList[i].clickStatus = 1
////                                break
////                            } else
//                                if (timeModel.equals(selectedNamelist.time!![i])){
//                                timeList[i].clickStatus = 2
//                                break
//                            }else{
//                                timeList[i].clickStatus = 0
//                            }
//
//                    }
//                }
                setAdapter(timeList)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == 0) {

                    val bp = data!!.extras!!.get("data") as Bitmap
                    val resized = Bitmap.createScaledBitmap(bp, 100, 100, true)
                    phtobitmap = resized
                    val conv_bm = getRoundedRectBitmap(resized, 100)
                    companyimage.setImageBitmap(null)
                    companyimage.setImageBitmap(conv_bm)
                    Log.e("TAG", "select event for photo=$resultCode")
                    //customerImg=getBytes(conv_bm);
                }
//                Log.e("TAG", "select event for photo=$resultCode")
            }
            18 -> {
                handleBigCameraPhoto()
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
            FilePickerConst.REQUEST_CODE_DOC -> {


                if (resultCode === Activity.RESULT_OK && attr.data != null) {
                    docPaths = ArrayList()
                    docPaths.addAll(data!!.getStringArrayListExtra(KEY_SELECTED_DOCS)!!)
                }

//
//                val selectedFileUri: Uri = data!!.getData()!!
                Log.i(TAG, "selectedFileUri : ${docPaths[0]}")
//                Log.i(TAG, "Selected File Path:${selectedFileUri.lastPathSegment}")
//                val uri: Uri = data.data!!
//                val file = File(uri.path) //create path from uri
//
//                val split: List<String> = file.getPath().split("home:") //split the path.
//
//                var selectedFilePath =  "" //assign it to a string(your choice).
////                Log.i(TAG, "Selected File Path:$selectedFilePath")
////
//
//                if ("content".equals(uri.scheme, ignoreCase = true)) {
//                    val projection = arrayOf(MediaStore.Images.Media.DATA)
//                    var cursor: Cursor? = null
//                    try {
//                        cursor = context!!.contentResolver.query(uri, projection, null, null, null)
//                        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                        if (cursor.moveToFirst()) {
//                            selectedFilePath= cursor.getString(column_index)
//                        }
//                    } catch (e: Exception) {
//                        Log.e(TAG,e.localizedMessage)
//                    }
//                }
//                if (selectedFilePath != null && !selectedFilePath.equals("")) {
//                                    Log.e(TAG,"filepath : " +selectedFilePath)
//                } else {
//                    Toast.makeText(activity!!, "Cannot upload file to server", Toast.LENGTH_SHORT).show()
//                }

                val filepath: String = docPaths[0] //assign it to a string(your choice).
                Log.e(TAG, "filepath : " + filepath)
                try {
                    if (resultCode == Activity.RESULT_OK) {
                        try {

                            val file = FileReader(filepath)
                            Log.e(TAG, "file : " + file)
                            val buffer = BufferedReader(file)
                            var line = ""
                            var iteration = 0
                            while (buffer.readLine().also({ line = it }) != null) {
                                if (iteration == 0) {
                                    iteration++;
                                    continue;
                                }
                                val str: ArrayList<String> = line.split(",") as ArrayList<String> // defining
                                Log.e(TAG, "str : " + str)
                                if (str.size > 0) {
//                                    Log.e(TAG,"list : " +str)
//                                    list.add( PatientAppointmentTable(str[1],str[2],str[3],str[4],str[5],str[6],str[7]))
                                }
                            }
                            Log.e(TAG, "List size demo")
                            Log.e(TAG, "List size ${list.size}")
//                            patientRegisterDao.insert(list)

                        } catch (e: Exception) {
                            Log.e(TAG, e.localizedMessage)
                            Log.e(TAG, "List size ${list.size}")
                            patientRegisterDao.insert(list)
                        }
                    } else {
                        Toast.makeText(activity!!, "Only csv file allowed", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.localizedMessage)
                }


            }
        }
    }

    private fun handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            customCompressImage()
            imagepath.add(mCurrentPhotoPath.toString())
            Log.e(TAG, "handleBigCameraPhoto: path : " + mCurrentPhotoPath!!)
            mCurrentPhotoPath = null
        }
    }

    fun customCompressImage() {
        if (actualImage != null) {
            try {
                compressedImage = Compressor(activity).setMaxWidth(540).setMaxHeight(500).setQuality(50).setCompressFormat(Bitmap.CompressFormat.JPEG).compressToFile(actualImage!!)
                setCompressedImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun setCompressedImage() {
        val e: FileNotFoundException
        var imagesModel: ImagesModel
        val bitmap = BitmapFactory.decodeFile(compressedImage!!.absolutePath)
        try {
            val outputStream = FileOutputStream(actualImage!!)
            val fileOutputStream: FileOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            fileOutputStream = outputStream
        } catch (e3: FileNotFoundException) {
            e = e3
            e.printStackTrace()
            imagelist.add(bitmapUtility.getStringImage(bitmap))
            imagesModel = ImagesModel()
            imagesModel.bitmap = bitmap
            imgList.add(imagesModel)
            uploadimageAdapter = UploadimageAdapter(imgList, activity!!)
            recyclerView_doc_img.adapter = uploadimageAdapter
        }

        imagelist.add(bitmapUtility.getStringImage(bitmap))
        imagesModel = ImagesModel()
        imagesModel.bitmap = bitmap
        imgList.add(imagesModel)
        //        Log.e(TAG, "setCompressedImage: Image List Size : "+list.size() );
        if (imgList.size > 0) {
            //            imgmsg.setVisibility( View.VISIBLE );
            //            dottedview.setVisibility( View.GONE );
            recyclerView_doc_img.visibility = View.VISIBLE
        }
        uploadimageAdapter = UploadimageAdapter(imgList, activity!!)
        recyclerView_doc_img.adapter = uploadimageAdapter
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

    override fun onItemClickSpecialist(id: String) {

    }

    override fun onClickCountEmpSpecialist(count: Int) {

    }

    override fun onClickSpecialist(items: DoctorRegisterTable) {

    }

    override fun onItemClick(id: String) {

    }

    override fun onClickCountEmp(count: Int) {

    }

    override fun onClick(items: DoctorRegisterTable) {

    }

    override fun onIemClick() {

    }
}

