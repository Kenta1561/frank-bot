package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import frank.commandPrefix

class HelpHandler : MessageHandler() {

    override fun processMessage(channel: MessageChannel, args: List<String>) {
        channel.createMessage("[WIP] Command Overview").subscribe()
    }

    override fun isValidRequest(args: List<String>) = true

    override fun usage() = "Use $commandPrefix help to show available commands."

}
