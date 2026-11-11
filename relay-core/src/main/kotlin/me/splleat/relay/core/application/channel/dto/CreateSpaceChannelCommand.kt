package me.splleat.relay.core.application.channel.dto

data class CreateSpaceChannelCommand(
    val userId: Long,
    val spaceId: Long,
    val channelName: String,
)
