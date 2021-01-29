package frank.bot.handlers.message

import discord4j.core.`object`.entity.channel.MessageChannel
import frank.api.Requester
import frank.api.response.LocationSearchResponse
import frank.api.service.LocationService
import frank.commandPrefix
import frank.util.apiEmbedTemplate

class LocationSearchHandler(
    private val requester: Requester,
    private val locationService: LocationService
) : MessageHandler() {

    override fun processMessage(channel: MessageChannel, args: List<String>) {
        val query = args.subList(2, args.size).joinToString(" ")
        requester.request(locationService.getStationsByName(query)) { response ->
            channel.createMessage { msg -> msg.setEmbed(getLocationEmbed(query, response)) }.subscribe()
        }
    }

    private fun getLocationEmbed(query: String, response: LocationSearchResponse) = apiEmbedTemplate.andThen { spec ->
        spec.setTitle("Location results for $query")
        response.locationWrappers.take(9).map { w -> w.location }.forEach {
            spec.addField(it.name, it.extId, true)
        }
    }

    override fun isValidMessage(args: List<String>) = args.size > 2

    override fun usage() = "$commandPrefix search <query>"

}
