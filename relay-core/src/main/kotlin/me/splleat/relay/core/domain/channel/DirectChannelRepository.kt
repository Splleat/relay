package me.splleat.relay.core.domain.channel

import org.springframework.data.jpa.repository.JpaRepository

interface DirectChannelRepository : JpaRepository<DirectChannel, Long> {
    fun findByUserLowIdAndUserHighId(
        userLowId: Long,
        userHighId: Long,
    ): DirectChannel?
}
