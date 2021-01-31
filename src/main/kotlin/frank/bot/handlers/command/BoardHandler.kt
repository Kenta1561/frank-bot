package frank.bot.handlers.command

import discord4j.core.`object`.entity.channel.MessageChannel
import frank.api.Requester
import frank.api.response.BoardResponse
import frank.api.service.BoardService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import frank.util.getDateTimeString
import frank.util.isExtId
import retrofit2.Call

class BoardHandler(private val boardService: BoardService) : CommandHandler<BoardResponse>() {

    override fun createRequest(args: List<String>): Call<BoardResponse> {
        return boardService.getDepartures(args[2])
    }

    override fun processResponse(requester: Requester, channel: MessageChannel, response: BoardResponse) {
        channel.createMessage { msg -> msg.setEmbed(getEmbed(response)) }.subscribe()
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

    override fun isValidRequest(args: List<String>) = (args.size == 3) && isExtId(args[2])

    override fun usage() = "$commandPrefix departures <station>"

}
