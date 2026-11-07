package me.splleat.relay.core.application.channel.dto

data class DirectChannelGetOrCreateCommand(
    val userId: Long,
    val targetId: Long,
)
