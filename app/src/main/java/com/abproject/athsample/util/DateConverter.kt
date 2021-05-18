package com.abproject.athsample.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abolfazl on 5/13/21
 */
object DateConverter {

    fun convertStringToDateStringFormat(calendar: String): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH)
        cal.time = sdf.parse(calendar)
        val date = DateFormat.format("dd", cal.time).toString()
        val month = DateFormat.format("MM", cal.time).toString()
        return "${date}.${month}"
    }

    fun provideDate(): Date {
        return Calendar.getInstance().time
    }

    fun convertDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH)
        return dateFormat.format(date)
    }
}