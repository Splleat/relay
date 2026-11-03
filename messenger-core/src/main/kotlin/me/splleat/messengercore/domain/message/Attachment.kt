package me.splleat.messengercore.domain.message

import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity

@Entity
@Table(name = "attachments")
open class Attachment private constructor(
    messageId: Long,
    originalName: String,
    url: String,
    size: Long
) : BaseEntity() {

    var messageId: Long = messageId
        protected set

    var originalName: String = originalName
        protected set

    var url: String = url
        protected set

    var size: Long = size
        protected set

    companion object {
        fun create(messageId: Long, originalName: String, url: String, size: Long) =
            Attachment(messageId, originalName, url, size)
    }
}