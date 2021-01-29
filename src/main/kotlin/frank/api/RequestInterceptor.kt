package frank.api

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(private val accessId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedUrl = request
            .url()
            .newBuilder()
            .addQueryParameter("accessId", accessId)
            .addQueryParameter("format", "json")
            .build()
        println("Request sent.")
        return chain.proceed(request.newBuilder().url(updatedUrl).build())
    }

}
