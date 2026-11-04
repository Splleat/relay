package me.splleat.relay.core.domain.message

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.relay.core.domain.SoftDeletableEntity
import java.util.*

@Entity
@Table(name = "messages")
open class Message private constructor(
    userId: Long,
    channelId: Long,
    content: String,
    idempotencyKey: UUID
) : SoftDeletableEntity() {

    var userId: Long = userId
        protected set

    var channelId: Long = channelId
        protected set

    var content: String = content
        protected set

    var idempotencyKey: UUID = idempotencyKey
        protected set

    companion object {
        fun create(userId: Long, channelId: Long, content: String, idempotencyKey: UUID) =
            Message(userId, channelId, content, idempotencyKey)
    }
}