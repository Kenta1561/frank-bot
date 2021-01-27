package frank.util

import java.time.format.DateTimeFormatter

fun getDateFormat() = DateTimeFormatter.ISO_LOCAL_DATE

fun getTimeFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
