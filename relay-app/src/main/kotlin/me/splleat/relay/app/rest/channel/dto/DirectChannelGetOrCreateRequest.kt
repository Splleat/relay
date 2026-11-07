package me.splleat.relay.app.rest.channel.dto

import jakarta.validation.constraints.NotNull
import me.splleat.relay.core.application.channel.dto.DirectChannelGetOrCreateCommand

data class DirectChannelGetOrCreateRequest(

    @field:NotNull
    val targetId: Long
) {
    fun toCommand(userId: Long) =
        DirectChannelGetOrCreateCommand(userId = userId, targetId = targetId)
}
