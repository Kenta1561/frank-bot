package frank.bot.handlers.command

import discord4j.core.`object`.entity.Message
import frank.api.Requester
import frank.bot.handlers.MessageHandler

class HelpHandler : MessageHandler {

    override fun handle(requester: Requester, message: Message) {
        message.channel.flatMap { channel -> channel.createMessage("[WIP] Command Overview") }.subscribe()
    }

}
