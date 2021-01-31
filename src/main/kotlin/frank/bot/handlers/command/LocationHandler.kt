package frank.bot.handlers.command

import discord4j.core.`object`.entity.channel.MessageChannel
import frank.api.Requester
import frank.api.response.LocationResponse
import frank.api.service.LocationService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import retrofit2.Call

class LocationHandler(private val locationService: LocationService) : CommandHandler<LocationResponse>() {

    private var query: String = ""

    override fun createRequest(args: List<String>): Call<LocationResponse> {
        query = args.subList(2, args.size).joinToString(" ")
        return locationService.getStationsByName(query)
    }

    override fun processResponse(requester: Requester, channel: MessageChannel, response: LocationResponse) {
        channel.createMessage { msg -> msg.setEmbed(getEmbed(query, response)) }.subscribe()
    }

    private fun getEmbed(query: String, response: LocationResponse) = apiEmbedTemplate.andThen { spec ->
        spec.setTitle("Location results for $query")
        response.locationWrappers.take(9).map { w -> w.location }.forEach {
            spec.addField(it.name, it.extId, true)
        }
    }

    override fun isValidRequest(args: List<String>) = args.size > 2

    override fun usage() = "$commandPrefix search <query>"

}
