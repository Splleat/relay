package me.splleat.relay.support

import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tools.jackson.module.kotlin.jsonMapper

private val jsonMapper = jsonMapper()

fun MockMvc.postJson(url: String, body: Any, dsl: MockHttpServletRequestDsl.() -> Unit = {}) = post(url) {
    with(csrf())

    contentType = MediaType.APPLICATION_JSON
    content = jsonMapper.writeValueAsString(body)
    dsl()
}