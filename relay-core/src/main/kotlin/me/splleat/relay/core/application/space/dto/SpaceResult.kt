package me.splleat.relay.core.application.space.dto

import me.splleat.relay.core.domain.channel.Channel
import me.splleat.relay.core.domain.space.Space
import me.splleat.relay.core.domain.space.SpaceType

data class SpaceResult(
    val id: Long,
    val name: String,
    val type: SpaceType,
    val channels: List<SpaceChannelSummaryResult>,
) {
    companion object {
        fun from(space: Space, channels: List<Channel>) =
            SpaceResult(space.requireId(), space.name, space.spaceType, channels.map(SpaceChannelSummaryResult::from))
    }
}
