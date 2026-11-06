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
    fun validateSpaceMember(userId: Long, spaceId: Long) {
        if (!repository.existsByUserIdAndSpaceId(userId, spaceId)) {
            throw BusinessException(SpaceErrorCode.NOT_FOUND)
        }
    }
}
