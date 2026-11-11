package me.splleat.relay.app.rest.message.dto

import me.splleat.relay.core.application.message.dto.MessageResult
import java.time.LocalDateTime

data class MessageResponse(
    val messageId: Long,
    val channelId: Long,
    val senderId: Long,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(result: MessageResult) =
            MessageResponse(
                result.messageId,
                result.channelId,
                result.senderId,
                result.content,
                result.createdAt,
            )
    }
}
