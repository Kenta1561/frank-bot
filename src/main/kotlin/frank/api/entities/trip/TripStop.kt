package frank.api.entities.trip

import frank.api.entities.location.Location
import frank.util.getTimeFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class TripStop(extId: String, name: String, val date: String, val time: String) : Location(extId, name) {

    fun getTimeString(): String = DateTime.parse(time, DateTimeFormat.forPattern("HH:mm:ss")).toString(getTimeFormat())

    override fun toString() = "${getTimeString()} $name"

}
