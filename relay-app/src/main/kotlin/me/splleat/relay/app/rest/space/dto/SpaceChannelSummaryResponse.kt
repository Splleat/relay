package me.splleat.relay.app.rest.space.dto

import me.splleat.relay.core.application.space.dto.SpaceChannelSummaryResult

data class SpaceChannelSummaryResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(result: SpaceChannelSummaryResult) =
            SpaceChannelSummaryResponse(result.id, result.name)
    }
}
