package me.splleat.relay.core.support

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@Import(MysqlTestContainer::class, DatabaseCleaner::class)
@ExtendWith(DatabaseCleanupExtension::class)
@ActiveProfiles("test")
annotation class IntegrationTest
