package me.splleat.relay.app.rest.message.dto

import me.splleat.relay.core.application.message.dto.ChannelMessagesResult

data class ChannelMessagesResponse(
    val messages: List<MessageResponse>,
    val hasPrev: Boolean,
    val hasNext: Boolean,
) {
    companion object {
        fun from(result: ChannelMessagesResult) =
            ChannelMessagesResponse(result.messages.map(MessageResponse::from), result.hasPrev, result.hasNext)
    }
}
