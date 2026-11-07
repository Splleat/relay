package me.splleat.relay.core.domain.space

import me.splleat.relay.core.common.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceMemberService(
    private val repository: SpaceMemberRepository
) {

    @Transactional
    fun registerOwner(userId: Long, spaceId: Long): SpaceMember {
        val owner = SpaceMember.createOwner(userId, spaceId)

        return repository.save(owner)
    }

    @Transactional(readOnly = true)
    fun getAllSpaceIdByUser(userId: Long): List<Long> {
        return repository.findAllSpaceIdByUserId(userId)
    }

    @Transactional(readOnly = true)
    fun getSpaceMember(userId: Long, spaceId: Long): SpaceMember {
        return repository.findByUserIdAndSpaceId(userId, spaceId)
            ?: throw BusinessException(SpaceMemberErrorCode.NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun getAllUserIds(spaceId: Long): List<Long> {
        return repository.findAllUserIdsBySpaceId(spaceId)
    }

    @Transactional(readOnly = true)
    fun validShareSpace(userId: Long, targetId: Long) {
        if (!repository.existsSharedSpace(userId, targetId)) {
            throw BusinessException(SpaceMemberErrorCode.NOT_FOUND)
        }
    }
}
