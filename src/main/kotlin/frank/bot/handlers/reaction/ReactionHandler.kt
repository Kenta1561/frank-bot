package frank.bot.handlers.reaction

import discord4j.core.event.domain.message.ReactionAddEvent

abstract class ReactionHandler {

    fun handle(event: ReactionAddEvent) {
        if(isValidReaction(event)) processReaction(event)
    }

    protected abstract fun processReaction(event: ReactionAddEvent)

    protected abstract fun isValidReaction(event: ReactionAddEvent) : Boolean

}
