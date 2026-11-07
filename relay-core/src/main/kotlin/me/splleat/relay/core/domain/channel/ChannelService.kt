package me.splleat.relay.core.domain.channel

import me.splleat.relay.core.common.BusinessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(
    private val channelRepository: ChannelRepository,
    private val directChannelRepository: DirectChannelRepository,
) {

    @Transactional
    fun registerSpace(spaceId: Long, name: String): Channel {
        val channel = Channel.createSpaceChannel(spaceId, name)

        return channelRepository.save(channel)
    }

    @Transactional
    fun registerDirect(userId: Long, targetId: Long): DirectChannel {
        val channel = Channel.createDirectChannel()
            .also(channelRepository::save)

        val directChannel = DirectChannel.create(channel.requireId(), userId, targetId)

        return directChannelRepository.save(directChannel)
    }

    @Transactional(readOnly = true)
    fun findDirect(userId: Long, targetId: Long): DirectChannel? {
        val (userLowId, userHighId) = DirectChannel.sortId(userId, targetId)

        return directChannelRepository.findByUserLowIdAndUserHighId(userLowId, userHighId)
    }

    @Transactional(readOnly = true)
    fun getChannel(channelId: Long): Channel {
        return channelRepository.findByIdOrNull(channelId)
            ?: throw BusinessException(ChannelErrorCode.NOT_FOUND)
    }
}
