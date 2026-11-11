package me.splleat.relay.core.application.channel.dto

data class GetChannelCommand(
    val userId: Long,
    val channelId: Long,
)
