package com.example.medicalmanagement.db.dao

import androidx.room.*
import com.example.medicalmanagement.db.table.DoctorRegisterTable

@Dao
interface DoctorRegisterDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<DoctorRegisterTable>)

    @Update
    fun update(doctorList:DoctorRegisterTable)

        @Query("Select * from doctor_register_table")
  fun  getall():List<DoctorRegisterTable>

    @Query("Select specialist from doctor_register_table")
    fun  getSpecialist():List<String>

    //login
    @Query("select * from doctor_register_table where email=:email and password=:password")
    fun login( email:String,password:String): DoctorRegisterTable

    @Query("Select * from doctor_register_table where specialist=:specialist")
    fun  getSpecialist(specialist:String):List<DoctorRegisterTable>

    @Query("Delete from doctor_register_table where id=:id")
    fun  delete(id:Int)

}
