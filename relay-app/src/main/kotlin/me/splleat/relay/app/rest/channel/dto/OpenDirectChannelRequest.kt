package me.splleat.relay.app.rest.channel.dto

import jakarta.validation.constraints.NotNull
import me.splleat.relay.core.application.channel.dto.OpenDirectChannelCommand

data class OpenDirectChannelRequest(

    @field:NotNull
    val targetId: Long
) {
    fun toCommand(userId: Long) =
        OpenDirectChannelCommand(userId = userId, targetId = targetId)
}
