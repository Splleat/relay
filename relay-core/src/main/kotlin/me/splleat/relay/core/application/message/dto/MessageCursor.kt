package me.splleat.relay.core.application.message.dto

sealed interface MessageCursor {
    data object Newest : MessageCursor
    data class Before(val messageId: Long) : MessageCursor
    data class After(val messageId: Long) : MessageCursor
}
