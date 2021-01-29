package frank.bot.handlers.message

import discord4j.core.`object`.entity.channel.MessageChannel
import frank.commandPrefix

class HelpHandler : MessageHandler() {

    override fun processMessage(channel: MessageChannel, args: List<String>) {
        channel.createMessage("[WIP] Command Overview").subscribe()
    }

    override fun isValidMessage(args: List<String>) = true

    override fun usage() = "Use $commandPrefix help to show available commands."

}
