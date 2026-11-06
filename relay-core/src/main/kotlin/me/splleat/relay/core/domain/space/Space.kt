package me.splleat.relay.core.domain.space

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.relay.core.domain.SoftDeletableEntity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "spaces")
open class Space private constructor(
    name: String,
    spaceType: SpaceType,
) : SoftDeletableEntity() {

    var name: String = name
        protected set

    @JdbcTypeCode(SqlTypes.VARCHAR)
    var spaceType: SpaceType = spaceType
        protected set

    companion object {
        fun createPersonal() =
            Space("개인 스페이스", SpaceType.PERSONAL)
        fun createShared(name: String) =
            Space(name, SpaceType.SHARED)
    }
}

enum class SpaceType {
    PERSONAL,
    SHARED,
}
