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

    @Query("""
        SELECT EXISTS (
            SELECT 1
            FROM SpaceMember a, SpaceMember b
            WHERE a.spaceId = b.spaceId
                AND a.userId = :userId
                AND b.userId = :targetId
        )
    """)
    fun existsSharedSpace(
        userId: Long,
        targetId: Long,
    ): Boolean

    @Query("""
        SELECT sm.userId
        FROM SpaceMember sm
        WHERE sm.spaceId = :spaceId
    """)
    fun findAllUserIdsBySpaceId(spaceId: Long): List<Long>

    fun findByUserId(userId: Long): List<SpaceMember>

    fun findByUserIdAndSpaceId(
        userId: Long,
        spaceId: Long,
    ): SpaceMember?
}
