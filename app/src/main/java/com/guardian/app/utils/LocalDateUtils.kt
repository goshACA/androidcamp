package com.guardian.app.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class LocalDateUtils{
    companion object{
        private val localDateTimeFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        fun getCurrentDateString(): String = localDateTimeFormatter.format(Date())
    }
}