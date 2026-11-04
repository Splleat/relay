package me.splleat.messengercore.domain

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime


@MappedSuperclass
abstract class SoftDeletableEntity: BaseEntity() {
    var deletedAt: LocalDateTime? = null
        protected set

    val isDeleted: Boolean get() = deletedAt != null

    protected fun markDeleted() {
        check(deletedAt == null) { "이미 삭제된 엔티티" }
        deletedAt = LocalDateTime.now()
    }
}