package me.splleat.relay.core.application.message.dto

data class MessageCreatedEvent(
    val message: MessageResult
) {
    companion object {
        fun from(result: MessageResult) =
            MessageCreatedEvent(result)
    }
}
