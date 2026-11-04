package me.splleat.messengercore.domain.channel

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity

@Entity
@Table(name = "channel_members")
open class ChannelMember private constructor(
    userId: Long,
    channelId: Long
) : BaseEntity() {
    var userId: Long = userId
        protected set

    var channelId: Long = channelId
        protected set

    var lastReadMessageId: Long? = null
        protected set

    companion object {
        fun create(userId: Long, channelId: Long) =
            ChannelMember(userId, channelId)
    }
}