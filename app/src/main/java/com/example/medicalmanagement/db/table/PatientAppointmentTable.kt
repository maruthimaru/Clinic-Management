package com.example.medicalmanagement.db.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
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

    @SerializedName("imagelist")
    @Expose
    var imagelist = ArrayList<String>()

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

    constructor(id: Int?, pName: String?, imagelist: ArrayList<String>, phone: String?, age: String?, email: String?, dId: String?, dName: String?, specialist: String?) {
        this.id = id
        this.pName = pName
        this.imagelist = imagelist
        this.phone = phone
        this.age = age
        this.email = email
        this.dId = dId
        this.dName = dName
        this.specialist = specialist
    }
}
