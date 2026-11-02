package me.splleat.messengercore.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.splleat.messengercore.domain.BaseEntity
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Column(name = "email", unique = true)
    var email: String,

    @Column(name = "password_hash")
    var passwordHash: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "status_message")
    var statusMessage: String? = null,

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null
): BaseEntity()