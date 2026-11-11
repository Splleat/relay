package me.splleat.relay.core.fixture

import me.splleat.relay.core.domain.channel.ChannelMember
import me.splleat.relay.core.domain.channel.ChannelMemberRepository

fun createChannelMember(
    userId: Long,
    channelId: Long,
): ChannelMember = ChannelMember.create(userId, channelId)

fun ChannelMemberRepository.saveChannelMember(
    userId: Long,
    channelId: Long,
): ChannelMember = save(createChannelMember(userId, channelId))
