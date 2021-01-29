package frank.bot.handlers.message

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import frank.util.getCommandArgs

abstract class MessageHandler {

    fun handle(message: Message) {
        val args = message.getCommandArgs()
        message.channel.subscribe { channel ->
            if(isValidMessage(args)) {
                processMessage(channel, args)
            } else {
                channel.createMessage(usage()).subscribe()
            }
        }
    }

    protected abstract fun processMessage(channel: MessageChannel, args: List<String>)

    protected abstract fun isValidMessage(args: List<String>) : Boolean

    protected abstract fun usage() : String

}
