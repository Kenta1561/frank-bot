package frank.api.entities.trip

import frank.api.entities.location.Location
import frank.util.getTimeFormat
import java.time.LocalTime

class TripStop(extId: String, name: String, val date: String, val time: String) : Location(extId, name) {

    //Use proper time formatting instead of substring
    override fun toString() = "${time.substring(0, 5)} $name"

    fun getTimeString() = LocalTime.parse(time).format(getTimeFormat())

}
