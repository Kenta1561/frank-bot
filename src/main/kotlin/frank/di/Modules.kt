package frank.di

import frank.api.RequestInterceptor
import frank.api.Requester
import frank.api.service.LocationService
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val module = module {

    single { RequestInterceptor(System.getenv("RMV_ACCESS_ID")) }

    single {
        okhttp3.OkHttpClient.Builder()
            .addInterceptor(get<RequestInterceptor>())
            .build()
    }

    single {
        retrofit2.Retrofit.Builder()
            .client(get())
            .baseUrl("https://www.rmv.de/hapi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { Requester() }

    //Services
    single { get<retrofit2.Retrofit>().create(LocationService::class.java) }

}
