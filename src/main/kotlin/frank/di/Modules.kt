package frank.di

import frank.api.RequestInterceptor
import frank.api.Requester
import frank.api.service.LocationService
import frank.bot.handlers.HelpHandler
import frank.bot.handlers.LocationSearchHandler
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module {

    single { RequestInterceptor(System.getenv("RMV_ACCESS_ID")) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<RequestInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://www.rmv.de/hapi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { Requester() }

    //Services
    single { get<Retrofit>().create(LocationService::class.java) }

    //MessageHandlers
    single { LocationSearchHandler(get(), get()) }
    single { HelpHandler() }

}
