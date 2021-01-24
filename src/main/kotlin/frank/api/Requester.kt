package frank.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Requester {

    fun <T> request(call: Call<T>, simpleCallback: SimpleCallback<T>) {
        call.enqueue(RequestCallback(simpleCallback))
    }

    fun interface SimpleCallback<T> {
        fun onResponse(data: T?)
    }

    private class RequestCallback<T>(val simpleCallback: SimpleCallback<T>) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            simpleCallback.onResponse(response.body())
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            t.printStackTrace()
        }

    }

}
