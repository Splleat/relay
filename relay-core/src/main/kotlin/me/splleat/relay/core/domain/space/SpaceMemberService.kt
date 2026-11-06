package me.splleat.relay.core.domain.space

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
}
