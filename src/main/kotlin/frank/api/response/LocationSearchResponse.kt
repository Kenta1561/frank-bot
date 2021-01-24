package frank.api.response

import com.google.gson.annotations.SerializedName
import frank.api.entities.location.LocationWrapper

class LocationSearchResponse(
    requestId: String,
    @SerializedName("stopLocationOrCoordLocation") val locationWrappers: List<LocationWrapper>
) : Response(requestId)
