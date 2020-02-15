package com.example.medicalmanagement.db.dao

import androidx.room.*
import com.example.medicalmanagement.db.table.ScheduleTime

@Dao
interface ScheduleTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(scheduleTime: ScheduleTime)

    @Delete
    fun delete(scheduleTime: ScheduleTime)

    @Query("update schudle_time set timeing=:time where id=:id")
    fun update(time:String,id :Int)

    @Query("Select * from schudle_time")
    fun getTime():List<ScheduleTime>
}