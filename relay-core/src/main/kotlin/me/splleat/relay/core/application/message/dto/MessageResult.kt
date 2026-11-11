package me.splleat.relay.core.application.message.dto

import me.splleat.relay.core.domain.message.Message
import java.time.LocalDateTime

data class MessageResult(
    val messageId: Long,
    val channelId: Long,
    val senderId: Long,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(message: Message) =
            MessageResult(
                message.requireId(),
                message.channelId,
                message.userId,
                message.content,
                message.requireCreatedAt()
            )
    }
}
