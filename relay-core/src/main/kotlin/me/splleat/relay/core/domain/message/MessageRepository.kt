package me.splleat.relay.core.domain.message

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MessageRepository : JpaRepository<Message, Long> {
    fun findByUserIdAndIdempotencyKey(
        userId: Long,
        idempotencyKey: UUID
    ): Message?
}
