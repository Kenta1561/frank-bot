package frank.bot.handlers.message

import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.spec.EmbedCreateSpec
import frank.api.Requester
import frank.api.response.BoardResponse
import frank.api.service.BoardService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import frank.util.getDateTimeString
import frank.util.isExtId
import java.util.function.Consumer

class BoardHandler(
    private val requester: Requester,
    private val boardService: BoardService
) : MessageHandler() {

    override fun processMessage(channel: MessageChannel, args: List<String>) {
        requester.request(boardService.getDepartures(args[2])) { response ->
            channel.createMessage { msg -> msg.setEmbed(getEmbed(response)) }.subscribe()
        }
    }

    private fun getEmbed(response: BoardResponse) = apiEmbedTemplate.andThen { spec ->
        response.elements.take(5).forEach { board ->
            spec.addField(
                board.getActualDateTime()?.getDateTimeString() ?: board.getPlannedDateTime().getDateTimeString(),
                "${board.product.line} to ${board.direction}",
                false
            )
        }
    }

    override fun isValidMessage(args: List<String>) = (args.size == 3) && isExtId(args[2])

    override fun usage() = "$commandPrefix departures <station>"

}
