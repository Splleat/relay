package me.splleat.relay.core.application.channel.dto

data class OpenDirectChannelCommand(
    val userId: Long,
    val targetId: Long,
)
