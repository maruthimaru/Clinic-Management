package com.example.medicalmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.medicalmanagement.R
import com.example.medicalmanagement.db.AppDatabase
import com.example.medicalmanagement.db.dao.PatientRegisterDao
import com.example.medicalmanagement.helper.Constants

class PacientLoginActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var submit: TextView
    lateinit var pacient_register:TextView
    lateinit var appDatabase: AppDatabase
    lateinit var patientRegisterDao: PatientRegisterDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacient_login)
        email=findViewById(R.id.mobile)
        password=findViewById(R.id.pins)
        submit=findViewById(R.id.logein)
        pacient_register=findViewById(R.id.pacient_register)
        appDatabase = AppDatabase.getDatabase(this)
        patientRegisterDao=appDatabase.patientRegisterDao()
        submit.setOnClickListener {
            askAppointment()
        }
        pacient_register.setOnClickListener {
            newReg()
        }
    }

    private fun newReg() {
        intent = Intent(this@PacientLoginActivity,PatientRegisterActivity::class.java)
        startActivity(intent)
        finish()
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
            var patientDetails=patientRegisterDao.login(Email,pass)
            if (patientDetails!=null) {
                intent = Intent(this@PacientLoginActivity, PatientMainActivity::class.java)
                var bundle = Bundle()
                bundle.putSerializable(Constants.patientList, patientDetails)
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
