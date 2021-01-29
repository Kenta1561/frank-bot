package frank.bot.handlers.message

import discord4j.common.util.Snowflake
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.`object`.reaction.ReactionEmoji
import frank.api.Requester
import frank.api.response.TripSearchResponse
import frank.api.service.TripService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import frank.util.isExtId
import frank.util.reactionEmojis
import reactor.core.publisher.Flux

class TripSearchHandler(private val requester: Requester, private val tripService: TripService) : MessageHandler() {



    override fun processMessage(channel: MessageChannel, args: List<String>) {
        requester.request(tripService.getTrips(args[2], args[3])) { response ->
            channel
                .createMessage { msg -> msg.setEmbed(getTripResultEmbed(response)) }
                .map { msg -> requester.cacheTripResponse(msg, response) }
                .flatMapMany { msg -> addNumberReactions(msg) }
                .subscribe()
        }
    }

    private fun getTripResultEmbed(response: TripSearchResponse) = apiEmbedTemplate.andThen { spec ->
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

    override fun isValidMessage(args: List<String>) : Boolean {
        return args.size == 4 && listOf(args[2], args[3]).all { isExtId(it) }
    }

    override fun usage() = "$commandPrefix trip <from> <to>"

}
