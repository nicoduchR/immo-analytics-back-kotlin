package com.gnirps.api.config.properties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "immo-back")
data class ProjectProperties (
    val version: String
) {
    companion object {
        /**
         * The UUID string size
         */
        const val UUID_SIZE = 37
        const val VERSION = "1.0"
    }
}
