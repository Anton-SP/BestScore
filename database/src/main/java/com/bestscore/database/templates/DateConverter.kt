package com.bestscore.database.templates

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {
    private val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    @TypeConverter
    fun toDate(date: String): Date {
        return df.parse(date)
    }

    @TypeConverter
    fun toDateString(date: Date): String {
        return df.format(date)
    }
}