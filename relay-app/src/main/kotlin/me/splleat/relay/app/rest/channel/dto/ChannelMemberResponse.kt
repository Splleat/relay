package me.splleat.relay.app.rest.channel.dto

import me.splleat.relay.core.application.channel.dto.ChannelMemberResult

data class ChannelMemberResponse(
    val userId: Long,
    val name: String,
    val imageUrl: String?
) {
    companion object {
        fun from(result: ChannelMemberResult) =
            ChannelMemberResponse(result.userId, result.name, result.imageUrl)
    }
}
