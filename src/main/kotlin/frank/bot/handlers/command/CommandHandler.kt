package frank.bot.handlers.command

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import frank.api.Requester
import frank.api.response.Response
import frank.bot.handlers.MessageHandler
import frank.util.getCommandArgs
import retrofit2.Call

abstract class CommandHandler<T : Response> : MessageHandler {

    //TODO restructure
    override fun handle(requester: Requester, message: Message) {
        val args = message.getCommandArgs()
        message.channel.subscribe { channel ->
            if(isValidRequest(args)) {
                requester.request(createRequest(args)) { response ->
                    if(response.isValid()) {
                        processResponse(requester, channel, response)
                    } else {
                        channel.createMessage("Invalid response").subscribe()
                    }
                }
            } else {
                showUsage(channel)
            }
        }
    }

    private fun showUsage(channel: MessageChannel) = channel.createMessage(usage()).subscribe()

    protected abstract fun createRequest(args: List<String>) : Call<T>

    protected abstract fun processResponse(requester: Requester, channel: MessageChannel, response: T)

    protected abstract fun isValidRequest(args: List<String>) : Boolean

    protected abstract fun usage() : String

}
