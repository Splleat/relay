package me.splleat.messengercore.domain.space

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.SoftDeletableEntity

@Entity
@Table(name = "spaces")
open class Space private constructor(
    name: String
) : SoftDeletableEntity() {

    var name: String = name
        protected set

    companion object {
        fun create(name: String) =
            Space(name)
    }
}