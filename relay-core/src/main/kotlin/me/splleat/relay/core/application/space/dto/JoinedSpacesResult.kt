package me.splleat.relay.core.application.space.dto

import me.splleat.relay.core.domain.space.Space
import me.splleat.relay.core.domain.space.SpaceType

data class JoinedSpacesResult(
    val spaces: List<SpaceSummaryResult>
) {
    companion object {
        fun from(spaces: List<Space>) =
            JoinedSpacesResult(spaces.map(SpaceSummaryResult::from))
    }
}
