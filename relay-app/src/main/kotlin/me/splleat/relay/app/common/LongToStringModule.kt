package me.splleat.relay.app.common

import org.springframework.stereotype.Component
import tools.jackson.databind.module.SimpleModule
import tools.jackson.databind.ser.std.ToStringSerializer

@Component
class LongToStringModule : SimpleModule() {
    init {
        addSerializer(Long::class.javaObjectType, ToStringSerializer.instance)
        addSerializer(Long::class.javaPrimitiveType, ToStringSerializer.instance)
    }
}
