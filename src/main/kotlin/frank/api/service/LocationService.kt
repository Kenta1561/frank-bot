package frank.api.service

import frank.api.response.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("location.name")
    fun getStationsByName(
        @Query("input") input: String,
        @Query("type") type: String = "S"
    ) : Call<LocationResponse>

}
