package com.example.medicalmanagement.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medicalmanagement.db.table.DoctorRegisterTable

@Dao
interface DoctorRegisterDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<DoctorRegisterTable>)

        @Query("Select * from doctor_register_table")
  fun  getall():List<DoctorRegisterTable>

}
