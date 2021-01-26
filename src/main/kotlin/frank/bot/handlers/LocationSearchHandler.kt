package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import frank.api.Requester
import frank.api.response.LocationSearchResponse
import frank.api.service.LocationService
import frank.commandPrefix
import frank.util.apiEmbedTemplate
import frank.util.getCommandArgs

class LocationSearchHandler(
    private val requester: Requester,
    private val locationService: LocationService
) : MessageHandler() {

    override fun processMessage(message: Message) {
        val args = message.getCommandArgs()
        val channel = message.channel.block()!!
        if(args.size > 2) {
            val query = args.subList(2, args.size).joinToString(" ")
            requester.request(locationService.getStationsByName(query)) { data ->
                data?.let {
                    channel.createMessage { msg -> msg.setEmbed(getEmbed(query, it)) }.subscribe()
                }
            }
        } else {
            commandHelp(channel)
        }
    }

    private fun getEmbed(query: String, response: LocationSearchResponse) = apiEmbedTemplate.andThen { spec ->
        spec.setTitle("Location results for $query")
        response.locationWrappers.take(9).map { w -> w.location }.forEach {
            spec.addField(it.name, it.extId, true)
        }
    }

    override fun usage() = "$commandPrefix search <query>"

}
