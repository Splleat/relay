package me.splleat.relay.core.fixture

import me.splleat.relay.core.domain.message.Message
import me.splleat.relay.core.domain.message.MessageRepository
import java.util.UUID

fun createMessage(
    userId: Long,
    channelId: Long,
    content: String = "테스트 메시지",
    idempotencyKey: UUID = UUID.randomUUID(),
): Message = Message.create(userId, channelId, content, idempotencyKey)

fun MessageRepository.saveMessage(
    userId: Long,
    channelId: Long,
    content: String = "테스트 메시지",
    idempotencyKey: UUID = UUID.randomUUID()
): Message = save(createMessage(userId, channelId, content, idempotencyKey))
