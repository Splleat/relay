package me.splleat.relay.support

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithTestUserSecurityContextFactory::class)
annotation class WithTestUser(
    val id: Long = 1L,
)
