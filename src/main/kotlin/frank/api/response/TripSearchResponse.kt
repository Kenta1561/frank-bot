package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.trip.Trip

class TripSearchResponse(@SerializedName("Trip") val trips: List<Trip>, requestId: String) : Response(requestId) {

    //Possible IndexOutOfBoundsException
    fun getOrigin() = trips[0].getOrigin()

    //Possible IndexOutOfBoundsException
    fun getDestination() = trips[0].getDestination()

    fun getDescription() = "Your trip from ${getOrigin().name} to ${getDestination().name}"

}
