package frank

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.ReactionAddEvent
import frank.api.Requester
import frank.bot.handlers.command.*
import frank.bot.handlers.reaction.TripDetailHandler
import frank.di.module
import frank.util.getCommandArgs
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

const val commandPrefix = "!frank"

@KoinApiExtension
class FrankBot : KoinComponent {

    private val gateway = DiscordClient.create(System.getenv("DISCORD_TOKEN")).login().block()!!
    private val commandHandlers = mapOf<String, CommandHandler<*>>(
        "search" to get<LocationHandler>(),
        "trip" to get<TripHandler>(),
        "departures" to get<BoardHandler>()
    )

    init {
        with(gateway) {
            on(MessageCreateEvent::class.java)
                .map(MessageCreateEvent::getMessage)
                .filter { msg -> msg.getCommandArgs()[0] == commandPrefix }
                .filter { msg -> !msg.author.get().isBot }
                .subscribe { msg -> handleMessage(msg) }
            on(ReactionAddEvent::class.java)
                .filterWhen { event -> event.user.map { user -> !user.isBot }}
                .subscribe { event -> get<TripDetailHandler>().handle(event) }
            onDisconnect().block()
        }
    }

    private fun handleMessage(message: Message) {
        //Add help handling
        commandHandlers
            .getOrDefault(message.getCommandArgs().getOrNull(1), get<HelpHandler>())
            .handle(get<Requester>(), message)
    }

}

@KoinApiExtension
fun main() {
    startKoin { modules(module) }
    FrankBot()
}
