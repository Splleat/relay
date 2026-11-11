package me.splleat.relay.app.rest.space.dto

import me.splleat.relay.core.application.space.dto.JoinedSpacesResult
import me.splleat.relay.core.domain.space.SpaceType

data class JoinedSpacesResponse(
    val spaces: List<SpaceSummaryResponse>
) {
    companion object {
        fun from(result: JoinedSpacesResult) =
            JoinedSpacesResponse(result.spaces.map(SpaceSummaryResponse::from))
    }
}
