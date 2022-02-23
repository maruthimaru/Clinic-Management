package com.example.medicalmanagement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.medicalmanagement.db.dao.*
import com.example.medicalmanagement.db.table.*


@Database(
        entities = [DoctorRegisterTable::class,PatientRegisterTable::class,ScheduleTime::class,PatientAppointmentTable::class, HospitalRegisterTable::class],version = 13)
abstract class AppDatabase:RoomDatabase(){
    abstract fun hospitalregisterdao(): HospitalRegisterDao
    abstract fun doctorregisterdao(): DoctorRegisterDao
    abstract fun patientRegisterDao():PatientRegisterDao
    abstract fun schudleTimeDao():ScheduleTimeDao
    abstract fun patientAppointmentDao():PatientAppointmentDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        internal fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java, "Medical.db"
                        )
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
