package me.splleat.relay.core.application.space.dto

import me.splleat.relay.core.domain.channel.Channel

data class SpaceChannelSummaryResult(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(channel: Channel) =
            SpaceChannelSummaryResult(channel.requireId(), checkNotNull(channel.name) { "스페이스 채널에 이름 미존재" })
    }
}
