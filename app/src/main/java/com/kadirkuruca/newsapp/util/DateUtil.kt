package com.kadirkuruca.newsapp.util

import java.text.SimpleDateFormat
import java.util.*


fun changeDateFormat(strDate: String): String {
    val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val requiredSdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
    return requiredSdf.format(sourceSdf.parse(strDate))
}