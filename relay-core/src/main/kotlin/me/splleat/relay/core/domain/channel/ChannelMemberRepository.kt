package me.splleat.relay.core.domain.channel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChannelMemberRepository : JpaRepository<ChannelMember, Long> {
    fun existsByUserIdAndChannelId(
        userId: Long,
        channelId: Long,
    ): Boolean

    @Query("""
        SELECT cm.userId
        FROM ChannelMember cm
        WHERE cm.channelId = :channelId
    """)
    fun findUserIdsByChannelId(channelId: Long): List<Long>
}
