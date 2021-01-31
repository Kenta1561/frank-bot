package frank.bot.handlers.reaction

import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.spec.EmbedCreateSpec
import frank.api.Requester
import frank.api.entities.trip.Trip
import frank.util.reactionEmojis
import java.util.function.Consumer

class TripDetailHandler(private val requester: Requester) : ReactionHandler() {

    override fun processReaction(event: ReactionAddEvent) {
        val index = reactionEmojis.indexOf(event.emoji.asUnicodeEmoji().get().raw)
        val trip = requester.getCachedTripResponse(event.messageId).trips[index]
        event.channel.flatMap { channel ->
            channel.createMessage { msg -> msg.setEmbed(getEmbed(trip)) }
        }.subscribe()
        requester.forgetTripResponse(event.messageId)
        event.message.flatMap { msg -> msg.delete() }.subscribe()
    }

    private fun getEmbed(trip: Trip) = Consumer<EmbedCreateSpec> { spec ->
        spec.setTitle(trip.getDescription())
        trip.legListWrapper.legs.forEach { leg ->
            spec.addField(leg.getLine(), leg.getRoute(), false)
        }
    }

    override fun isValidReaction(event: ReactionAddEvent) : Boolean {
        return requester.hasTripResponse(event.messageId) &&
                reactionEmojis.contains(event.emoji.asUnicodeEmoji().get().raw)
    }

}
