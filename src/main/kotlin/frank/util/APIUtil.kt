package frank.util

import java.time.format.DateTimeFormatter

fun getDateFormat() = DateTimeFormatter.ISO_LOCAL_DATE

fun getTimeFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun isExtId(extId: String) = extId.matches(Regex("[0-9]{9}"))
