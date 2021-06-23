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

        /**
         * Version of the API. TODO : find a way to use it
         */
        const val VERSION = "1.0"

        /**
         * Version of the API. TODO : find a way to use it
         */
        const val DISTRICT_API_URL = "https://geo.api.gouv.fr/regions"
    }
}
