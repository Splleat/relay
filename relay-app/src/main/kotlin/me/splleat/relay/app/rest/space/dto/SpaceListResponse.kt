package me.splleat.relay.app.rest.space.dto

import me.splleat.relay.core.application.space.dto.SpaceListResult
import me.splleat.relay.core.domain.space.SpaceType

data class SpaceListResponse(
    val id: Long,
    val name: String,
    val type: SpaceType,
) {
    companion object {
        fun from(result: SpaceListResult) =
            SpaceListResponse(result.id, result.name, result.type)
    }
}
