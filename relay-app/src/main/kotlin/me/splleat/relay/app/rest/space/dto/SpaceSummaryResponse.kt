package me.splleat.relay.app.rest.space.dto

import me.splleat.relay.core.application.space.dto.SpaceSummaryResult
import me.splleat.relay.core.domain.space.SpaceType

data class SpaceSummaryResponse(
    val id: Long,
    val name: String,
    val type: SpaceType,
) {
    companion object {
        fun from(result: SpaceSummaryResult) =
            SpaceSummaryResponse(result.id, result.name, result.type)
    }
}
