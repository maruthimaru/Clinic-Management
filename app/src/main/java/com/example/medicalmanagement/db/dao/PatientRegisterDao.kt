package com.example.medicalmanagement.db.dao

import androidx.room.*
import com.example.medicalmanagement.db.table.PatientRegisterTable

@Dao
interface PatientRegisterDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<PatientRegisterTable>)

    @Update
    fun update(doctorList:PatientRegisterTable)

        @Query("Select * from patient_register_table")
  fun  getall():List<PatientRegisterTable>

    //login
    @Query("select * from patient_register_table where email=:email and password=:password")
    fun login( email:String,password:String): PatientRegisterTable

    @Query("Delete from patient_register_table where id=:id")
    fun  delete(id:Int)

}
