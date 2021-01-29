package frank.api

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import frank.api.response.TripSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Requester {

    private val tripResponseCache = HashMap<Snowflake, TripSearchResponse>()

    fun <T> request(call: Call<T>, simpleCallback: SimpleCallback<T>) {
        call.enqueue(RequestCallback(simpleCallback))
    }

    //region Trip response caching
    fun cacheTripResponse(message: Message, response: TripSearchResponse) = message.also {
        tripResponseCache[message.id] = response
    }

    fun hasTripResponse(messageId: Snowflake) = tripResponseCache.containsKey(messageId)

    fun getCachedTripResponse(messageId: Snowflake) = tripResponseCache.get(messageId)!!

    fun forgetTripResponse(messageId: Snowflake) {
        tripResponseCache.remove(messageId)
    }
    //endregion

    fun interface SimpleCallback<T> {
        fun onResponse(data: T)
    }

    private class RequestCallback<T>(val simpleCallback: SimpleCallback<T>) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let { simpleCallback.onResponse(it) }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            t.printStackTrace()
        }

    }

}
