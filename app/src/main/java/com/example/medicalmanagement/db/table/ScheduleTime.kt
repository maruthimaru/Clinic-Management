package com.example.medicalmanagement.db.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "schudle_time")
class ScheduleTime {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("id")
    var id:Int=0

    @SerializedName("id")
    lateinit var timeing:String

    var clickStatus:Int = 0

    constructor(timeing: String) {
        this.timeing = timeing
    }

//    constructor(timeing: String, clickStatus: Boolean) {
//        this.timeing = timeing
//        this.clickStatus = clickStatus
//    }


}