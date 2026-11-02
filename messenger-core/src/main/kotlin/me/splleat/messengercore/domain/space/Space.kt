package me.splleat.messengercore.domain.space

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity

@Entity
@Table(name = "spaces")
class Space(
    @Column(name = "name")
    var name: String
): BaseEntity()