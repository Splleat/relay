package me.splleat.relay.app.rest.message.dto

import me.splleat.relay.core.application.message.dto.SendMessageResult

data class SendMessageResponse(
    val isCreated: Boolean,
    val message: MessageResponse,
) {
    companion object {
        fun from(result: SendMessageResult) =
            SendMessageResponse(result.isCreated, MessageResponse.from(result.message))
    }
}
