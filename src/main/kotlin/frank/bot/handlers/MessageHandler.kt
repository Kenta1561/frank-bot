package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel

abstract class MessageHandler {

    abstract fun processMessage(message: Message)

    protected abstract fun usage() : String

    protected fun commandHelp(channel: MessageChannel) {
        channel.createMessage(usage()).block()
    }

}
