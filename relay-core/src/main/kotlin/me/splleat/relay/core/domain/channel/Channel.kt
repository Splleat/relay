package me.splleat.relay.core.domain.channel

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.relay.core.domain.SoftDeletableEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "channels")
open class Channel private constructor(
    spaceId: Long?,
    name: String?,
    channelType: ChannelType,
) : SoftDeletableEntity() {

    var spaceId: Long? = spaceId
        protected set

    var name: String? = name
        protected set

    @JdbcTypeCode(SqlTypes.VARCHAR)
    var channelType: ChannelType = channelType
        protected set

    companion object {
        fun createDirectChannel(spaceId: Long) =
            Channel(spaceId, null, ChannelType.DM)

        fun createPublicChannel(spaceId: Long, name: String) =
            Channel(spaceId, name, ChannelType.SPACE)
    }
}

enum class ChannelType {
    SPACE,
    DM,
    GROUP_DM
}
