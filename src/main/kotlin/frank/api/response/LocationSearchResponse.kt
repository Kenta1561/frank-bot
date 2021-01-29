package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.location.LocationWrapper

class LocationSearchResponse(
    @SerializedName("stopLocationOrCoordLocation") val locationWrappers: List<LocationWrapper>
)
