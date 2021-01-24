package frank.api.entities.location

import com.google.gson.annotations.SerializedName

data class LocationWrapper(@SerializedName("StopLocation") val location: Location)
