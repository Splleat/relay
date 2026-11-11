package me.splleat.relay.app.rest.channel.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import me.splleat.relay.core.application.channel.dto.CreateSpaceChannelCommand

data class CreateSpaceChannelRequest(

    @field:NotBlank
    @field:Size(max = 50)
    val channelName: String
) {
    fun toCommand(userId: Long, spaceId: Long) =
        CreateSpaceChannelCommand(userId = userId, spaceId = spaceId, channelName = channelName)
}
