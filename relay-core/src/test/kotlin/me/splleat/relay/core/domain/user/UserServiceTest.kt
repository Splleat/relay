package me.splleat.relay.core.domain.user

import io.mockk.bdd.given
import io.mockk.bdd.then
import io.mockk.mockk
import me.splleat.relay.core.common.BusinessException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Test
    fun `사용 중이지 않은 이메일로 사용자를 등록하면 저장한다`() {
        // given
        val user = User.register("테스트", "test@test.com", "hashed-password")
        given { userRepository.existsByEmail(user.email) } returns false
        given { userRepository.save(user) } answers { firstArg() }

        // when
        val saved = userService.register(user)

        // then
        assertThat(saved)
            .isSameAs(user)
        then(exactly = 1) { userRepository.save(user) }
    }

    @Test
    fun `사용 중인 이메일로 사용자를 등록하면 예외가 발생한다`() {
        // given
        val user = User.register("테스트", "test@test.com", "hashed-password")
        given { userRepository.existsByEmail(user.email) } returns true

        // when
        val exception = assertThrows<BusinessException> { userService.register(user) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(UserErrorCode.EMAIL_DUPLICATED)
        then(exactly = 0) { userRepository.save(any()) }
    }

    @Test
    fun `등록되지 않은 이메일로 사용자를 조회하면 예외가 발생한다`() {
        // given
        val email = "test@test.com"

        given { userRepository.findByEmail(email) } returns null

        // when
        val exception = assertThrows<BusinessException> { userService.getUserForLogin(email) }

        // then
        assertThat(exception.errorCode)
            .isEqualTo(UserErrorCode.INVALID_CREDENTIALS)
    }
}