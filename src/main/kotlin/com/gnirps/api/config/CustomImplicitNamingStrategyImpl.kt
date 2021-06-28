package com.gnirps.api.config

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl

/**
 * Custom implementation of Hibernate implicit strategy
 */
class CustomImplicitNamingStrategyImpl : ImplicitNamingStrategyJpaCompliantImpl() {
    override fun determineJoinColumnName(source: ImplicitJoinColumnNameSource): Identifier? {
        val name: String = if (source.nature == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
            || source.attributePath == null) {
            (
                transformEntityName(source.entityNaming)
                        + source.referencedColumnName.text.capitalize()
            )
        } else {
            (
                transformAttributePath(source.attributePath)
                        + source.referencedColumnName.text.capitalize()
            )
        }

        return toIdentifier(name, source.buildingContext)
    }
}
