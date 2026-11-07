package me.splleat.relay.app.rest.channel.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import me.splleat.relay.core.application.channel.dto.SpaceChannelCreateCommand

data class SpaceChannelCreateRequest(

    @field:NotBlank
    @field:Size(max = 50)
    val channelName: String
) {
    fun toCommand(userId: Long, spaceId: Long) =
        SpaceChannelCreateCommand(userId = userId, spaceId = spaceId, channelName = channelName)
}
