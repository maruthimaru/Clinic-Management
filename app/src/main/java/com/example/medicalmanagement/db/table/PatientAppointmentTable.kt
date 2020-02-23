package com.example.medicalmanagement.db.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.medicalmanagement.db.dao.DoctorRegDataConversion
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "patient_appointment_table")
class PatientAppointmentTable: Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    @Expose
    var id:Int?=null

    @SerializedName("p_name")
    @Expose
    var pName: String? = null

    @TypeConverters(DoctorRegDataConversion::class)
    @SerializedName("imagelist")
    @Expose
    var imagelist : List<String>?=null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("age")
    @Expose
    var age: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("d_id")
    @Expose
    var dId: String? = null

    @SerializedName("d_name")
    @Expose
    var dName: String? = null

    @SerializedName("specialist")
    @Expose
    var specialist: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("suggestion")
    @Expose
    var suggestion: String? = null

    @SerializedName("rejected")
    @Expose
    var isRejects: Boolean = false

    @TypeConverters(DoctorRegDataConversion::class)
    @SerializedName("time")
    @Expose
    var time : List<String>?=null

    constructor( pName: String?, imagelist: List<String>?, phone: String?, age: String?, email: String?, dId: String?, dName: String?, specialist: String?, date: String?, time: List<String>?) {
        this.pName = pName
        this.imagelist = imagelist
        this.phone = phone
        this.age = age
        this.email = email
        this.dId = dId
        this.dName = dName
        this.specialist = specialist
        this.date = date
        this.time = time
    }
}
