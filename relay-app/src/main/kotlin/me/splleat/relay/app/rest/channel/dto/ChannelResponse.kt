package me.splleat.relay.app.rest.channel.dto

import me.splleat.relay.core.application.channel.dto.ChannelResult
import me.splleat.relay.core.domain.channel.ChannelType

data class ChannelResponse(
    val id: Long,
    val channelType: ChannelType,
    val name: String?,
    val channelMemberList: List<ChannelMemberResponse>,
) {
    companion object {
        fun from(result: ChannelResult) =
            ChannelResponse(
                result.id,
                result.channelType,
                result.name,
                result.channelMemberList.map(ChannelMemberResponse::from))
    }
}
