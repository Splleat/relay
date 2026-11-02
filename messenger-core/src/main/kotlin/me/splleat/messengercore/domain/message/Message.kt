package me.splleat.messengercore.domain.message

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity
import java.util.UUID

@Entity
@Table(
    name = "messages",
    indexes = [
        Index(name = "idx_messages_channel_message", columnList = "channel_id, id")
    ]
)
class Message(
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "channel_id")
    val channelId: Long,

    @Column(name = "content")
    val content: String,

    @Column(name = "idempotency_key", unique = true)
    val idempotencyKey: UUID
): BaseEntity()