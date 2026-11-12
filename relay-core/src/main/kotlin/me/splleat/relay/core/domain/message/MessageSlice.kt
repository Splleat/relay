package me.splleat.relay.core.domain.message

data class MessageSlice(
    val messages: List<Message>,
    val hasMore: Boolean,
)
