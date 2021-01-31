package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.trip.Trip

class TripResponse(
    @SerializedName("Trip") val trips: List<Trip>
) : Response {

    override fun isValid() = trips.isNotEmpty()

    fun getOrigin() = trips[0].getOrigin()

    fun getDestination() = trips[0].getDestination()

    fun getDescription() = "Your trip from ${getOrigin().name} to ${getDestination().name}"

}
