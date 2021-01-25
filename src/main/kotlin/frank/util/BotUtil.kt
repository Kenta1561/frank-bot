package frank.util

import discord4j.core.`object`.entity.Message

fun Message.getCommandArgs() = content.split(" ")
