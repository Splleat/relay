package me.splleat.messengercore.domain.channel

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import me.splleat.messengercore.domain.BaseEntity

@Entity
@Table(
    name = "channel_members",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_channel_user_settings_user_channel", columnNames = ["user_id", "channel_id"])
    ]
)
class ChannelMember(
    @Column(name = "channel_id")
    val channelId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "last_read_message_id")
    var lastReadMessageId: Long? = null
) : BaseEntity()