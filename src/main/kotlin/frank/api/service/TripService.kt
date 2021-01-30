package frank.api.service

import frank.api.response.TripResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TripService {

    @GET("trip")
    fun getTrips(
        @Query("originExtId") originExtId: String,
        @Query("destExtId") destExtId: String
    ) : Call<TripResponse>

}
