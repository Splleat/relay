package me.splleat.relay.core.domain.space

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpaceMemberRepository : JpaRepository<SpaceMember, Long> {

    @Query("""
        SELECT sm.spaceId
        FROM SpaceMember sm
        WHERE sm.userId = :userId
    """)
    fun findAllSpaceIdByUserId(userId: Long): List<Long>

    fun findByUserId(userId: Long): List<SpaceMember>

    fun existsByUserIdAndSpaceId(
        userId: Long,
        spaceId: Long,
    ): Boolean
}
