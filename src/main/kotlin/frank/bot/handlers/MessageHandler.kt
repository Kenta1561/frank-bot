package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import frank.api.Requester

interface MessageHandler {

    fun handle(requester: Requester, message: Message)

}
