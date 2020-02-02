package com.example.medicalmanagement.activity

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao
import com.example.medicalmanagement.helper.Constants

class DoctorLoginActivity : AppCompatActivity() {
     lateinit var email:EditText
    lateinit var password:EditText
    lateinit var submit: TextView
    lateinit var appDatabase: AppDatabase
    lateinit var doctorRegisterDao: DoctorRegisterDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_login)
        email=findViewById(R.id.Email_id)
        password=findViewById(R.id.password)
        submit=findViewById(R.id.logein)
        appDatabase = AppDatabase.getDatabase(this)
        doctorRegisterDao=appDatabase.doctorregisterdao()
        submit.setOnClickListener {
            askAppointment()
        }
    }

    private fun askAppointment(){
        val Email=email.text.toString()
        val pass=password.text.toString()
        if (Email.isNullOrEmpty()){
            email.requestFocus()
            email.error = "Please enter your email id"
        }else if (pass.isNullOrEmpty()){
            password.requestFocus()
            password.error = "Please enter your password"
        }
        else{
            var doctorDetails=doctorRegisterDao.login(Email,pass)
            if (doctorDetails!=null) {
                intent = Intent(this@DoctorLoginActivity, DoctorMainActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable(Constants.doctorList, doctorDetails)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
                Toast.makeText(applicationContext, "Login successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
