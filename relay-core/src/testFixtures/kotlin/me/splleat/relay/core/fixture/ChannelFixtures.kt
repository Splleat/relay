package me.splleat.relay.core.fixture

import me.splleat.relay.core.domain.channel.Channel
import me.splleat.relay.core.domain.channel.ChannelRepository

fun ChannelRepository.saveDirectChannel(): Channel = save(Channel.createDirectChannel())

fun ChannelRepository.saveSpaceChannel(
    spaceId: Long,
    name: String = "스페이스 채널"
): Channel = save(Channel.createSpaceChannel(spaceId, name))
