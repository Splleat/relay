package me.splleat.relay.core.support

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.beans.factory.getBean
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanupExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        SpringExtension.getApplicationContext(context)
            .getBean<DatabaseCleaner>()
            .clean()
    }
}
