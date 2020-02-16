package com.example.medicalmanagement.db.table

import androidx.annotation.NonNull
import androidx.room.*
import com.example.medicalmanagement.db.dao.DoctorRegDataConversion
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "doctor_register_table")
class DoctorRegisterTable: Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    var id:Int?=null

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    var name: String? = null

    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    @Expose
    var phone: String? = null

    @SerializedName("image")
    @ColumnInfo(name = "image")
    @Expose
    var image: String? = null

    @SerializedName("email")
    @ColumnInfo(name = "email")
    @Expose
    var email: String? = null

    @SerializedName("specialist")
    @ColumnInfo(name = "specialist")
    @Expose
    var specialist: String? = null

    @TypeConverters(DoctorRegDataConversion::class)
    @SerializedName("time")
    @ColumnInfo(name = "time")
    var time: List<String?>? = null

    @SerializedName("password")
    @ColumnInfo(name = "password")
    @Expose
    var password: String? = null


    constructor(name: String?, phone: String?, image: String?, email: String?, specialist: String?, time: List<String?>?, password: String?) {
        this.name = name
        this.phone = phone
        this.image = image
        this.email = email
        this.specialist = specialist
        this.time = time
        this.password = password
    }
}
