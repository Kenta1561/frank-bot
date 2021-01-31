package frank.bot.handlers.command

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import frank.api.Requester
import frank.api.response.TripResponse
import frank.api.service.TripService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import frank.util.isExtId
import frank.util.reactionEmojis
import reactor.core.publisher.Flux
import retrofit2.Call

class TripHandler(private val tripService: TripService) : CommandHandler<TripResponse>() {

    override fun createRequest(args: List<String>): Call<TripResponse> {
        return tripService.getTrips(args[2], args[3])
    }

    override fun processResponse(requester: Requester, channel: MessageChannel, response: TripResponse) {
        channel
            .createMessage { msg -> msg.setEmbed(getEmbed(response)) }
            .map { msg -> requester.cacheTripResponse(msg, response) }
            .flatMapMany { msg -> addNumberReactions(msg) }
            .subscribe()
    }

    private fun getEmbed(response: TripResponse) = apiEmbedTemplate.andThen { spec ->
        spec.setTitle(response.getDescription())
        spec.setDescription("Select a reaction to show details.")
        response.trips.forEachIndexed { index, trip ->
            spec.addField(
                "#${index + 1}",
                "${trip.getOrigin().getTimeString()} :arrow_forward: ${trip.getDestination().getTimeString()}",
                false
            )
        }
    }

    private fun addNumberReactions(message: Message) : Flux<Void> {
        return Flux.fromIterable(reactionEmojis)
            .map { s -> ReactionEmoji.unicode(s) }
            .take(5)
            .flatMap { emoji -> message.addReaction(emoji) }
    }

    override fun isValidRequest(args: List<String>) : Boolean {
        return args.size == 4 && listOf(args[2], args[3]).all { isExtId(it) }
    }

    override fun usage() = "$commandPrefix trip <from> <to>"

}
