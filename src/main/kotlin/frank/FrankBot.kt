package frank

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import frank.bot.handlers.HelpHandler
import frank.bot.handlers.LocationSearchHandler
import frank.bot.handlers.MessageHandler
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
    private val messageHandlers = mapOf<String, MessageHandler>(
        "search" to get<LocationSearchHandler>()
    )

    init {
        with(gateway) {
            on(MessageCreateEvent::class.java)
                .map(MessageCreateEvent::getMessage)
                .filter { msg -> msg.getCommandArgs()[0] == commandPrefix }
                .filter { msg -> !msg.author.get().isBot }
                .map { msg -> handleMessage(msg) }
                .subscribe()
            onDisconnect().block()
        }
    }

    private fun handleMessage(message: Message) {
        messageHandlers.getOrDefault(message.getCommandArgs().getOrNull(1), get<HelpHandler>()).processMessage(message)
    }

}

@KoinApiExtension
fun main() {
    startKoin { modules(module) }
    FrankBot()
}
