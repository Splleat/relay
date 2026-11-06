package me.splleat.relay.core.domain.space

import org.springframework.data.jpa.repository.JpaRepository

interface SpaceMemberRepository : JpaRepository<SpaceMember, Long> {
    fun findByUserId(userId: Long): List<SpaceMember>
}
