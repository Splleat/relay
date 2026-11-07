package me.splleat.relay.core.domain.channel

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.relay.core.domain.BaseEntity

@Entity
@Table(name = "direct_channels")
open class DirectChannel private constructor(
    channelId: Long,
    userLowId: Long,
    userHighId: Long,
) : BaseEntity() {
    var channelId: Long = channelId
        protected set

    var userLowId: Long = userLowId
        protected set

    var userHighId: Long = userHighId
        protected set

    companion object {
        fun create(channelId: Long, userId: Long, targetId: Long): DirectChannel {
            val (userLowId, userHighId) = sortId(userId, targetId)

            return DirectChannel(channelId, userLowId, userHighId)
        }

        fun sortId(userId: Long, targetId: Long): Pair<Long, Long> {
            return if (userId < targetId) userId to targetId else targetId to userId
        }
    }
}
