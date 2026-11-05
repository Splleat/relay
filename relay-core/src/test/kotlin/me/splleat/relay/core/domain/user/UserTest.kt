package me.splleat.relay.core.domain.user

import me.splleat.relay.core.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class UserTest {

    @Test
    fun `일치하지 않는 비밀번호로 로그인하면 예외가 발생하고 lastLoginAt이 변경되지 않는다`() {
        // given
        val invalidPassword = "invalid-password"
        val savedPassword = "hashed-password"
        val user = User.register("테스트", "test@test.com", savedPassword)
        val at = LocalDateTime.of(2026, 11, 5, 19, 41)

        // when
        val exception = assertThrows<BusinessException> { user.login(invalidPassword, { _, _ -> false }, at) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(UserErrorCode.INVALID_CREDENTIALS)
        assertThat(user.lastLoginAt)
            .isNull()
    }

    @Test
    fun `일치하는 비밀번호로 로그인하면 lastLoginAt이 at이 된다`() {
        // given
        val rawPassword = "raw-password"
        val savedPassword = "hashed-password"
        val user = User.register("테스트", "test@test.com", savedPassword)
        val at = LocalDateTime.of(2026, 11, 5, 19, 41)

        // when
        assertDoesNotThrow { user.login(rawPassword, { _, _ -> true }, at) }

        // then
        assertThat(user.lastLoginAt)
            .isEqualTo(at)
    }

    @Test
    fun `탈퇴한 사용자가 틀린 비밀번호로 로그인하면 예외가 발생하고 lastLoginAt이 변경되지 않는다`() {
        // given
        val invalidPassword = "invalid-password"
        val savedPassword = "hashed-password"
        val user = User.register("테스트", "test@test.com", savedPassword)
        val at = LocalDateTime.of(2026, 11, 5, 19, 41)

        user.withdraw()

        // when
        val exception = assertThrows<BusinessException> { user.login(invalidPassword, { _, _ -> false }, at) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(UserErrorCode.INVALID_CREDENTIALS)
        assertThat(user.lastLoginAt)
            .isNull()
    }

    @Test
    fun `탈퇴한 사용자가 올바른 비밀번호로 로그인하면 예외가 발생하고 lastLoginAt이 변경되지 않는다`() {
        // given
        val rawPassword = "raw-password"
        val savedPassword = "hashed-password"
        val user = User.register("테스트", "test@test.com", savedPassword)
        val at = LocalDateTime.of(2026, 11, 5, 19, 41)

        user.withdraw()

        // when
        val exception = assertThrows<BusinessException> { user.login(rawPassword, { _, _ -> true }, at) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(UserErrorCode.DEACTIVATED)
        assertThat(user.lastLoginAt)
            .isNull()
    }
}
