package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import frank.commandPrefix

class HelpHandler : MessageHandler() {

    override fun processMessage(message: Message) {
        message.channel.flatMap { channel -> channel.createMessage("[WIP] Command Overview") }.subscribe()
    }

    override fun usage() = "Use $commandPrefix help to show available commands."

}
