package com.project.falconic_solutions.betsample.data.extension

import android.util.Log
import com.project.falconic_solutions.betsample.data.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "String_EXT"

fun String.formatDate(pattern: String): String {
    val fromFormatter = SimpleDateFormat(DateFormat.DateTimeDash.pattern, Locale("tr", "TR"))
    val toFormatter = SimpleDateFormat(pattern, Locale("tr", "TR"))
    return try {
        val date = fromFormatter.parse(this)
        val dateString = date?.let { toFormatter.format(it) } ?: ""
        return dateString
    } catch (e: Exception) {
        Log.e(TAG, "toDate: ", e)
        ""
    }
}