package com.project.falconic_solutions.betsample.data

enum class DateFormat(val pattern: String) {
    DateTimeDash("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    DateTimeSlash("dd/MM/yyyy HH:mm")
}