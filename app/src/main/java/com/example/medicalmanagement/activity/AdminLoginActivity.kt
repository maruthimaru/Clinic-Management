package com.example.medicalmanagement.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.medicalmanagement.R

class AdminLoginActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.submit -> {
                val id = adminid!!.text.toString()


                if (id == "") run {
                    adminid!!.requestFocus()
                    adminid!!.error = "please Enter company id"
                }  else {
                    //getNotification()
                    if(id=="1234") {
                        intent = Intent(this@AdminLoginActivity, AdminMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        adminid!!.error = "Invalid id"
                    }
                }
            }
        }
    }
    lateinit var adminid:EditText
    private var submit: TextView? = null
    lateinit var doctor_register:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        adminid=findViewById(R.id.admin_id)
        submit=findViewById(R.id.submit)
        doctor_register=findViewById(R.id.doctor_register)


        submit!!.setOnClickListener(this)

    }

}
