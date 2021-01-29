package frank.util

import discord4j.core.`object`.entity.Message
import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import java.util.function.Consumer

fun Message.getCommandArgs() = content.split(" ")

val reactionEmojis = listOf('\u0031', '\u0032', '\u0033', '\u0034', '\u0035').map { c -> "$c\u20e3" }

val embedTemplate = Consumer<EmbedCreateSpec> { spec -> spec.setColor(Color.DEEP_LILAC) }
val apiEmbedTemplate: Consumer<EmbedCreateSpec> = embedTemplate.andThen { spec ->
    spec.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Rhein-Main-Verkehrsverbund_logo.svg/1920px-Rhein-Main-Verkehrsverbund_logo.svg.png")
    spec.setFooter("Source: Rhein-Main-Verkehrsverbund", null)
}
