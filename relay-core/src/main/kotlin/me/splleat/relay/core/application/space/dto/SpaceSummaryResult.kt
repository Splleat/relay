package me.splleat.relay.core.application.space.dto

import me.splleat.relay.core.domain.space.Space
import me.splleat.relay.core.domain.space.SpaceType

data class SpaceSummaryResult(
    val id: Long,
    val name: String,
    val type: SpaceType
) {
    companion object {
        fun from(space: Space) =
            SpaceSummaryResult(space.requireId(), space.name, space.spaceType)
    }
}
