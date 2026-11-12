package me.splleat.relay.core.application.message.dto

data class GetChannelMessagesCommand(
    val userId: Long,
    val channelId: Long,
    val cursor: MessageCursor,
    val limit: Int,
)
