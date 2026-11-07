package me.splleat.relay.core.application.channel.dto

data class SpaceChannelCreateCommand(
    val userId: Long,
    val spaceId: Long,
    val channelName: String,
)
