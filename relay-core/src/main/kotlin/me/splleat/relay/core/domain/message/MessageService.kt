package me.splleat.relay.core.domain.message

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
}
