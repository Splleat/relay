package me.splleat.relay.core.support

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@Import(MysqlTestContainer::class, RedisTestContainer::class)
@ActiveProfiles("test")
annotation class IntegrationTest