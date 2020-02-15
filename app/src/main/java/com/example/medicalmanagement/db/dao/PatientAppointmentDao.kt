package com.example.medicalmanagement.db.dao

import androidx.room.*
import com.example.medicalmanagement.db.table.DoctorRegisterTable
import com.example.medicalmanagement.db.table.PatientAppointmentTable

@Dao
interface PatientAppointmentDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<PatientAppointmentTable>)

    @Update
    fun update(doctorList:PatientAppointmentTable)

        @Query("Select * from patient_appointment_table")
  fun  getall():List<PatientAppointmentTable>

    @Query("Delete from patient_appointment_table where id=:id")
    fun  delete(id:Int)

}
