package frank.api.entities.trip

import com.google.gson.annotations.SerializedName

class Trip(@SerializedName("LegList") val legListWrapper: LegListWrapper) {

    fun getOrigin() = legListWrapper.legs.first().origin

    fun getDestination() = legListWrapper.legs.last().destination

    fun getDescription() = "Your trip from ${getOrigin().name} to ${getDestination().name}"

    class LegListWrapper(@SerializedName("Leg") val legs: List<TripLeg>)

    class TripLeg(
        @SerializedName("Origin") val origin: TripStop,
        @SerializedName("Destination") val destination: TripStop,
        val direction: String,
        @SerializedName("Product") val product: Product?
    ) {

        fun getLine() : String {
            return product?.let {
                return@let "${it.line} to ${destination.name}"
            } ?: "Walk"
        }

        fun getRoute() = "$origin :arrow_forward: $destination"

    }

    class Product(val line: String)

}
