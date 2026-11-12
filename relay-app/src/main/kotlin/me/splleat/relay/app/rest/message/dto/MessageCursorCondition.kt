package me.splleat.relay.app.rest.message.dto

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Positive
import me.splleat.relay.core.application.message.dto.GetChannelMessagesCommand
import me.splleat.relay.core.application.message.dto.MessageCursor

data class MessageCursorCondition(
    val before: Long?,
    val after: Long?,

    @field:Positive
    @field:Max(100)
    val limit: Int = 20
) {
    @AssertTrue(message = "before와 after는 함께 지정할 수 없습니다")
    fun isValidCondition(): Boolean = before == null || after == null

    fun toCommand(userId: Long, channelId: Long): GetChannelMessagesCommand {
        val cursor = when {
            before != null -> MessageCursor.Before(before)
            after != null -> MessageCursor.After(after)
            else -> MessageCursor.Newest
        }

        return GetChannelMessagesCommand(
            userId = userId,
            channelId = channelId,
            cursor = cursor,
            limit = limit
        )
    }
}
