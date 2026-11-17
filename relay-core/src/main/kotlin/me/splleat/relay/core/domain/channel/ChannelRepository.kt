package me.splleat.relay.core.domain.channel

import org.springframework.data.jpa.repository.JpaRepository

interface ChannelRepository : JpaRepository<Channel, Long> {
    fun findAllBySpaceIdOrderByIdAsc(spaceId: Long): List<Channel>
}
