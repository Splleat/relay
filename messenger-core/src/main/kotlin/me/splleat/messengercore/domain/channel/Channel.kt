package me.splleat.messengercore.domain.channel

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "channels")
class Channel(
    @Column(name = "space_id")
    val spaceId: Long,

    @Column(name = "name")
    var name: String,

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "channel_type")
    var channelType: ChannelType
): BaseEntity()

enum class ChannelType {
    PUBLIC,
    DM
}