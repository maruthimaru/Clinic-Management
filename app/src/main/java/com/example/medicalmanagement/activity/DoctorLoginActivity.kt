package com.example.medicalmanagement.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.DoctorRegisterDao

class DoctorLoginActivity : AppCompatActivity() {
     lateinit var email:EditText
    lateinit var password:EditText
    lateinit var submit:Button
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
            doctorRegisterDao.login(Email,pass)
            Toast.makeText(applicationContext,"Login successfully",Toast.LENGTH_SHORT).show()

        }
    }
}
