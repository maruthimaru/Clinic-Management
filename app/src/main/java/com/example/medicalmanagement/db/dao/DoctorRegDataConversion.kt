package com.example.medicalmanagement.db.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type


class DoctorRegDataConversion:Serializable {

    @TypeConverter // note this annotation
    fun fromOptionValuesList(optionValues: List<String>): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter // note this annotation
    fun toOptionValuesList(optionValuesString: String?): List<String?>? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(optionValuesString, type)
    }
}