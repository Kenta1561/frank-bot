package frank.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

fun getDateFormat() = DateTimeFormat.forPattern("yyyy-MM-dd")

fun getTimeFormat(): DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")

fun getDateTime(date: String, time: String) : DateTime = DateTime.parse(
    date + time, DateTimeFormat.forPattern("yyyy-MM-ddHH:mm:ss")
)

fun getNullableDateTime(date: String?, time: String?) : DateTime? {
    return if(date != null && time != null) getDateTime(date, time) else null
}

fun DateTime.getDateTimeString() = toString(getTimeFormat())

fun isExtId(extId: String) = extId.matches(Regex("[0-9]{9}"))
