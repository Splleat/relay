package me.splleat.relay.app.rest.message.dto

import me.splleat.relay.core.application.message.dto.MessageSendResult

data class MessageSendResponse(
    val isCreated: Boolean,
    val message: MessageResponse,
) {
    companion object {
        fun from(result: MessageSendResult) =
            MessageSendResponse(result.isCreated, MessageResponse.from(result.message))
    }
}
