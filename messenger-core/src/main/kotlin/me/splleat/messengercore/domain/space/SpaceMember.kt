package me.splleat.messengercore.domain.space

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import me.splleat.messengercore.domain.BaseEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(
    name = "space_members",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_space_members_user_space", columnNames = ["user_id", "space_id"])
    ]
)
class SpaceMember(
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "space_id")
    val spaceId: Long,

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "space_role")
    var role: SpaceRole
) : BaseEntity()

enum class SpaceRole {
    OWNER,
    MEMBER
}