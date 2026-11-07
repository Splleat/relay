package me.splleat.relay.core.application.channel.dto

import me.splleat.relay.core.domain.channel.Channel
import me.splleat.relay.core.domain.channel.ChannelType
import me.splleat.relay.core.domain.user.User

data class ChannelResult(
    val id: Long,
    val channelType: ChannelType,
    val name: String?,
    val channelMemberList: List<ChannelMemberResult>,
) {
    companion object {
        fun from(channel: Channel, channelMembers: List<User>) =
            ChannelResult(
                channel.requireId(),
                channel.channelType,
                channel.name,
                channelMembers.map(ChannelMemberResult::from))
    }
}
