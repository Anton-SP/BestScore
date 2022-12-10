package com.bestscore.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun currentDate(): Date {
    val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = df.format(Date(System.currentTimeMillis()))
    return df.parse(date)
}
