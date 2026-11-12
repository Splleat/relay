package me.splleat.relay.core.domain.message

import org.springframework.data.domain.Limit
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class MessageService(
    private val repository: MessageRepository
) {

    @Transactional
    fun register(message: Message): Message {
        return repository.save(message)
    }

    @Transactional(readOnly = true)
    fun findMessage(userId: Long, idempotencyKey: UUID): Message? {
        return repository.findByUserIdAndIdempotencyKey(userId, idempotencyKey)
    }

    @Transactional(readOnly = true)
    fun getMessagesBeforeCursor(channelId: Long, cursorId: Long, limit: Int): MessageSlice {
        val messages = repository.findAllByChannelIdAndIdLessThanOrderByIdDesc(channelId, cursorId, Limit.of(limit + 1))

        return slice(messages, limit, reverse = true)
    }

    @Transactional(readOnly = true)
    fun getMessagesAfterCursor(channelId: Long, cursorId: Long, limit: Int): MessageSlice {
        val messages = repository.findAllByChannelIdAndIdGreaterThanOrderByIdAsc(channelId, cursorId, Limit.of(limit + 1))

        return slice(messages, limit, reverse = false)
    }

    @Transactional(readOnly = true)
    fun getNewestMessages(channelId: Long, limit: Int): MessageSlice {
        val messages = repository.findAllByChannelIdOrderByIdDesc(channelId, Limit.of(limit + 1))

        return slice(messages, limit, reverse = true)
    }

    private fun slice(messages: List<Message>, limit: Int, reverse: Boolean): MessageSlice {
        val sliceMessages = messages.take(limit)
            .let { if (reverse) it.asReversed() else it }

        return MessageSlice(sliceMessages, messages.size > limit)
    }
}
