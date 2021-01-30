package frank.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.time.format.DateTimeFormatter

fun getDateFormat() = DateTimeFormatter.ISO_LOCAL_DATE

fun getTimeFormat(): DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun getDateTime(date: String, time: String) : DateTime = DateTime.parse(
    date + time, DateTimeFormat.forPattern("yyyy-MM-ddHH:mm:ss")
)

fun getNullableDateTime(date: String?, time: String?) : DateTime? {
    return if(date != null && time != null) getDateTime(date, time) else null
}

fun isExtId(extId: String) = extId.matches(Regex("[0-9]{9}"))
