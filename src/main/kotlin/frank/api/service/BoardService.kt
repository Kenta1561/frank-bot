package frank.api.service

import frank.api.response.BoardResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BoardService {

    @GET("departureBoard")
    fun getDepartures(
        @Query("extId") extId: String,
        @Query("maxJourneys") maxJourneys: Int = 5
    ) : Call<BoardResponse>

}
