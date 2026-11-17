package me.splleat.relay.app.rest.space.dto

import me.splleat.relay.core.application.space.dto.SpaceResult
import me.splleat.relay.core.domain.space.SpaceType

data class SpaceResponse(
    val id: Long,
    val name: String,
    val type: SpaceType,
    val channels: List<SpaceChannelSummaryResponse>
) {
    companion object {
        fun from(result: SpaceResult) =
            SpaceResponse(result.id, result.name, result.type, result.channels.map(SpaceChannelSummaryResponse::from))
    }
}
