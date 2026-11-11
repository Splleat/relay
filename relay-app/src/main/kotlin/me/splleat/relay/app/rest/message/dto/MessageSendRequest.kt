package me.splleat.relay.app.rest.message.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import me.splleat.relay.core.application.message.dto.MessageSendCommand
import java.util.UUID

data class MessageSendRequest(

    @field:NotBlank
    @field:Size(max = 1000)
    val content: String,

    @field:NotNull
    val idempotencyKey: UUID
) {
    fun toCommand(userId: Long, channelId: Long) =
        MessageSendCommand(
            userId = userId,
            channelId = channelId,
            content = content,
            idempotencyKey = idempotencyKey,
        )
}
