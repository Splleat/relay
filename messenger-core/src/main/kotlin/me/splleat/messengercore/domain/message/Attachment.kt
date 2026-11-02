package me.splleat.messengercore.domain.message

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity

@Entity
@Table(name = "attachments")
class Attachment(
    @Column(name = "message_id")
    val messageId: Long,

    @Column(name = "original_name")
    var originalName: String,

    @Column(name = "url")
    var url: String,

    @Column(name = "size")
    var size: Long
): BaseEntity()