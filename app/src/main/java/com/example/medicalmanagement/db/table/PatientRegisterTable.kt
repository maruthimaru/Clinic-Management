package com.example.medicalmanagement.db.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "patient_register_table")
class PatientRegisterTable: Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    @Expose
    var id:Int?=null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("specialist")
    @Expose
    var specialist: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null


    constructor(name: String?, phone: String?, image: String?, email: String?, specialist: String?, password: String?) {
        this.name = name
        this.phone = phone
        this.image = image
        this.email = email
        this.specialist = specialist
        this.password = password
    }
}
