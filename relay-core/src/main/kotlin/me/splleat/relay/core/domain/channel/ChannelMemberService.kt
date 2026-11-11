package me.splleat.relay.core.domain.channel

import me.splleat.relay.core.common.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelMemberService(
    private val repository: ChannelMemberRepository
) {

    @Transactional
    fun register(userId: Long, channelId: Long): ChannelMember {
        if (repository.existsByUserIdAndChannelId(userId, channelId)) {
            throw BusinessException(ChannelErrorCode.CONFLICT)
        }

        val channelMember = ChannelMember.create(userId, channelId)

        return repository.save(channelMember)
    }

    @Transactional
    fun registerAll(userIds: List<Long>, channelId: Long) {
        val channelMembers = userIds.map { ChannelMember.create(it, channelId) }

        repository.saveAll(channelMembers)
    }

    @Transactional(readOnly = true)
    fun validateChannelMember(userId: Long, channelId: Long) {
        if (!repository.existsByUserIdAndChannelId(userId, channelId)) {
            throw BusinessException(ChannelErrorCode.NOT_FOUND)
        }
    }

    @Transactional(readOnly = true)
    fun getChannelMemberUserIds(channelId: Long): List<Long> {
        return repository.findUserIdsByChannelId(channelId)
    }

    @Transactional(readOnly = true)
    fun isChannelMember(userId: Long, channelId: Long): Boolean {
        return repository.existsByUserIdAndChannelId(userId, channelId)
    }
}
