package me.splleat.relay.core.domain.user

import me.splleat.relay.core.support.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

@IntegrationTest
class UserTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun `저장하면 TSID가 채워진다` () {
        // given
        val user = User.register("테스트", "test@test.com", "hashed-password")

        // when
        val saved = userRepository.save(user)

        // then
        assertThat(saved.id)
            .isNotNull()
    }
}