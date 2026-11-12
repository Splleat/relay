package me.splleat.relay.core.domain.message

import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MessageRepository : JpaRepository<Message, Long> {
    fun findByUserIdAndIdempotencyKey(
        userId: Long,
        idempotencyKey: UUID
    ): Message?

    fun findAllByChannelIdAndIdLessThanOrderByIdDesc(
        channelId: Long,
        idIsLessThan: Long,
        limit: Limit
    ): List<Message>

    fun findAllByChannelIdAndIdGreaterThanOrderByIdAsc(
        channelId: Long,
        idIsGreaterThan: Long,
        limit: Limit
    ): List<Message>

    fun findAllByChannelIdOrderByIdDesc(
        channelId: Long,
        limit: Limit
    ): List<Message>
}
