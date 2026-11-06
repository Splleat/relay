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
    type: SpaceType,
) : SoftDeletableEntity() {

    var name: String = name
        protected set

    @JdbcTypeCode(SqlTypes.VARCHAR)
    var type: SpaceType = type
        protected set

    companion object {
        fun createPersonal(name: String) =
            Space(name, SpaceType.PERSONAL)
        fun createPublic(name: String) =
            Space(name, SpaceType.SHARED)
    }
}

enum class SpaceType {
    PERSONAL,
    SHARED,
}
