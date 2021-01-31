package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.location.LocationWrapper

class LocationResponse(
    @SerializedName("stopLocationOrCoordLocation") val locationWrappers: List<LocationWrapper>
) : Response {

    override fun isValid() = locationWrappers.isNotEmpty()

}
