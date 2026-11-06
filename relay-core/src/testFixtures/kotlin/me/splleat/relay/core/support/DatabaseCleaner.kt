package me.splleat.relay.core.support

import org.springframework.jdbc.core.ConnectionCallback
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForList

class DatabaseCleaner(
    private val jdbcTemplate: JdbcTemplate,
) {
    private val tables: List<String> by lazy {
        jdbcTemplate.queryForList<String>(
            """
            SELECT table_name FROM information_schema.tables
            WHERE table_schema = DATABASE()
              AND table_type = 'BASE TABLE'
              AND table_name <> 'flyway_schema_history'
            """.trimIndent()
        ).filterNotNull()
    }

    fun clean() {
        jdbcTemplate.execute(ConnectionCallback { connection ->
            connection.createStatement()
                .use { statement ->
                    statement.execute("SET FOREIGN_KEY_CHECKS = 0")
                    tables.forEach { statement.execute("TRUNCATE TABLE `$it`") }
                    statement.execute("SET FOREIGN_KEY_CHECKS = 1")
                }
        })
    }
}
