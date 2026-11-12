package me.splleat.relay.core.application.message.dto

import me.splleat.relay.core.domain.message.MessageSlice

data class ChannelMessagesResult(
    val messages: List<MessageResult>,
    val hasPrev: Boolean,
    val hasNext: Boolean,
) {
    companion object {
        fun of(slice: MessageSlice, cursor: MessageCursor) = when (cursor) {
            MessageCursor.Newest -> ChannelMessagesResult(slice.messages.map(MessageResult::from), hasPrev = slice.hasMore, hasNext = false)
            is MessageCursor.After -> ChannelMessagesResult(slice.messages.map(MessageResult::from), hasPrev = true, hasNext = slice.hasMore)
            is MessageCursor.Before -> ChannelMessagesResult(slice.messages.map(MessageResult::from), hasPrev = slice.hasMore, hasNext = true)
        }
    }
}
