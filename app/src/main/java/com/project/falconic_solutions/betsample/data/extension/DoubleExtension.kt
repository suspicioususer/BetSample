package com.project.falconic_solutions.betsample.data.extension

import java.text.NumberFormat
import java.util.Locale

fun Double.formatAmount(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
    return formatter.format(this)
}