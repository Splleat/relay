package me.splleat.messengerapp.rest.user

import com.ninjasquad.springmockk.MockkBean
import io.mockk.bdd.given
import io.mockk.bdd.then
import me.splleat.messengercore.application.user.UserRegisterUseCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tools.jackson.module.kotlin.jsonMapper

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var userRegisterUseCase: UserRegisterUseCase

    @Test
    fun `사용자를 등록하면 201을 반환한다`() {
        // given
        val request = UserRegisterRequest("테스트", "test@test.com", "password")
        val expectedId = 1L

        given { userRegisterUseCase.execute(request.toCommand()) } returns expectedId

        // when
        val result = mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper().writeValueAsString(request)
        }

        // then
        result.andExpect {
            status { isCreated() }
            header { string("Location", "http://localhost/api/v1/users/${expectedId}") }
        }
    }

    @Test
    fun `잘못된 이메일 형식으로 사용자를 등록하면 400을 반환한다`() {
        // given
        val request = UserRegisterRequest("테스트", "invalid-email", "password")

        // when
        val result = mockMvc.post("/api/v1/users") {
            contentType = MediaType.APPLICATION_JSON
            content = jsonMapper().writeValueAsString(request)
        }

        // then
        result.andExpect {
            status { isBadRequest() }
        }

        then(exactly = 0) { userRegisterUseCase.execute(any()) }
    }
}