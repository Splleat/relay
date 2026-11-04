package me.splleat.relay.core.domain.space

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.relay.core.domain.BaseEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "space_members")
open class SpaceMember private constructor(
    userId: Long,
    spaceId: Long,
    spaceRole: SpaceRole
) : BaseEntity() {

    var userId: Long = userId
        protected set

    var spaceId: Long = spaceId
        protected set

    @JdbcTypeCode(SqlTypes.VARCHAR)
    var spaceRole: SpaceRole = spaceRole
        protected set

    companion object {
        fun createOwner(userId: Long, spaceId: Long) =
            SpaceMember(userId, spaceId, SpaceRole.OWNER)

        fun createMember(userId: Long, spaceId: Long) =
            SpaceMember(userId, spaceId, SpaceRole.MEMBER)
    }
}

enum class SpaceRole {
    OWNER,
    MEMBER
}