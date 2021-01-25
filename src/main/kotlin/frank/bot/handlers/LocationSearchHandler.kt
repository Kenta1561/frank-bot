package frank.bot.handlers

import discord4j.core.`object`.entity.Message
import frank.api.Requester
import frank.api.service.LocationService
import frank.commandPrefix
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
                    channel.createEmbed { embed ->
                        embed.run {
                            setTitle("Location results for $query")
                            it.locationWrappers.map { w -> w.location }.forEach {
                                addField(it.name, it.extId, true)
                            }
                        }
                    }.subscribe()
                }
            }
        } else {
            commandHelp(channel)
        }
    }

    override fun usage() = "$commandPrefix search <query>"

}