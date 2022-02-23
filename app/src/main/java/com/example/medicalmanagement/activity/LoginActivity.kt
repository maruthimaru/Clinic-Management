package com.example.medicalmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.medicalmanagement.R
import com.example.medicalmanagement.helper.CommonMethods

class LoginActivity : AppCompatActivity() {
    lateinit var admin_image:ImageView
    lateinit var doctor_image:ImageView
    internal lateinit var commonMethods: CommonMethods
    lateinit var pacient_image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        admin_image=findViewById(R.id.admin_image)
        doctor_image=findViewById(R.id.doctor_image)
        pacient_image=findViewById(R.id.pacient_image)
        commonMethods= CommonMethods(this)

        admin_image.setOnClickListener{
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }
        doctor_image.setOnClickListener{
            val intent = Intent(this, DoctorLoginActivity::class.java)
            startActivity(intent)
        }
        pacient_image.setOnClickListener{
            val intent = Intent(this, PacientLoginActivity::class.java)
            startActivity(intent)
        }
    }



}
