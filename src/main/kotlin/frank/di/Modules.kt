package frank.di

import frank.api.RequestInterceptor
import frank.api.Requester
import frank.api.service.BoardService
import frank.api.service.LocationService
import frank.api.service.TripService
import frank.bot.handlers.command.BoardHandler
import frank.bot.handlers.command.HelpHandler
import frank.bot.handlers.command.LocationHandler
import frank.bot.handlers.command.TripHandler
import frank.bot.handlers.reaction.TripDetailHandler
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
    single { get<Retrofit>().create(TripService::class.java) }
    single { get<Retrofit>().create(BoardService::class.java) }

    //MessageHandlers
    single { LocationHandler(get()) }
    single { TripHandler(get()) }
    single { BoardHandler(get()) }
    single { HelpHandler() }

    //ReactionHandlers
    single { TripDetailHandler(get()) }

}
