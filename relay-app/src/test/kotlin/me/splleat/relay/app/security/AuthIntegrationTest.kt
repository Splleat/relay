package me.splleat.relay.app.security

import jakarta.servlet.http.Cookie
import me.splleat.relay.app.rest.auth.dto.UserLoginRequest
import me.splleat.relay.app.rest.user.dto.UserRegisterRequest
import me.splleat.relay.core.application.user.UserLoginUseCase
import me.splleat.relay.core.support.IntegrationTest
import me.splleat.relay.core.support.RedisTestContainer
import me.splleat.relay.support.postJson
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import tools.jackson.module.kotlin.jsonMapper

@IntegrationTest
@Import(RedisTestContainer::class)
@AutoConfigureMockMvc
class AuthIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userLoginUseCase: UserLoginUseCase

    @BeforeEach
    fun setUp() {
        val request = UserRegisterRequest("테스트", "test@test.com", "password")

        mockMvc.postJson("/api/v1/users", request)
            .andExpect {
                status { isCreated() }
            }
    }

    @Test
    fun `로그인하면 세션 쿠키로 내 정보를 조회할 수 있다`() {
        // given
        val sessionCookie = login()

        // when
        val result = mockMvc.get("/api/v1/users/me") {
            cookie(sessionCookie)
        }

        // then
        result.andExpect {
            status { isOk() }
            jsonPath("$.email") { value("test@test.com") }
        }
    }

    @Test
    fun `쿠키 없이 내 정보를 조회하면 401을 반환한다`() {
        // when
        val result = mockMvc.get("/api/v1/users/me")

        // then
        result.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `로그아웃한 뒤 같은 쿠키로 내 정보를 조회하면 401을 반환한다`() {
        // given
        val sessionCookie = login()

        // when
        logout(sessionCookie)

        val result = mockMvc.get("/api/v1/users/me") {
            cookie(sessionCookie)
        }

        result.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `로그인하면 기존 세션은 폐기되고 새 세션이 발급된다`() {
        // given
        val sessionCookie1 = login()
        val sessionCookie2 = login(sessionCookie1)

        // when
        val result = mockMvc.get("/api/v1/users/me") {
            cookie(sessionCookie1)
        }

        Assertions.assertThat(sessionCookie1.value)
            .isNotEqualTo(sessionCookie2.value)
        result.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `CSRF 토큰 없이 상태 변경을 요청하면 403을 반환한다`() {
        // when
        val loginRequest = UserLoginRequest("test@test.com", "password")
        val result = mockMvc.post("/api/v1/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper().writeValueAsString(loginRequest)
        }

        // then
        result.andExpect {
            status { isForbidden() }
        }
    }

    private fun login(existingSession: Cookie? = null): Cookie {
        val loginRequest = UserLoginRequest("test@test.com", "password")
        val loginResponse = mockMvc.postJson("/api/v1/auth/login", loginRequest) {
            existingSession?.let { cookie(it) }
        }.andReturn()

        val sessionCookie = checkNotNull(loginResponse.response.getCookie("SESSION")) { "세션 쿠키 미존재" }

        return sessionCookie
    }

    private fun logout(sessionCookie: Cookie) {
        mockMvc.post("/api/v1/auth/logout") {
            with(SecurityMockMvcRequestPostProcessors.csrf())
            cookie(sessionCookie)
        }.andExpect {
            status { isNoContent() }
        }
    }
}
