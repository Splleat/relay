package me.splleat.relay.core.application.channel.dto

data class ChannelGetCommand(
    val userId: Long,
    val channelId: Long,
)
