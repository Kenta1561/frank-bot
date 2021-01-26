package frank.util

import discord4j.core.`object`.entity.Message
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import java.util.function.Consumer

fun Message.getCommandArgs() = content.split(" ")

val embedTemplate = Consumer<EmbedCreateSpec> { spec -> spec.setColor(Color.DEEP_LILAC) }

val apiEmbedTemplate: Consumer<EmbedCreateSpec> = embedTemplate.andThen { spec ->
    spec.setFooter("Source: Rhein-Main-Verkehrsverbund", null)
}
