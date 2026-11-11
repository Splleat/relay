package me.splleat.relay.core.application.message.dto

import me.splleat.relay.core.domain.message.Message
import java.util.UUID

data class MessageSendCommand(
    val userId: Long,
    val channelId: Long,
    val content: String,
    val idempotencyKey: UUID,
) {
    fun toEntity(): Message {
        return Message.create(userId, channelId, content, idempotencyKey)
    }
}
