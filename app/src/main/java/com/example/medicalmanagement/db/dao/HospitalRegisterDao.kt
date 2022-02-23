package com.example.medicalmanagement.db.dao

import androidx.room.*
import com.example.medicalmanagement.db.table.HospitalRegisterTable

@Dao
interface HospitalRegisterDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<HospitalRegisterTable>)

    @Update
    fun update(doctorList:HospitalRegisterTable)

        @Query("Select * from hospital_register_table")
  fun  getall():List<HospitalRegisterTable>

    @Query("Delete from hospital_register_table where id=:id")
    fun  delete(id:Int)

}
