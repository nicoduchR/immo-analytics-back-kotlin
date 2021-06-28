package com.gnirps.api.town.model

import com.gnirps.api.config.properties.ProjectProperties
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Zipcode(
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = ProjectProperties.UUID_SIZE)
    val id: UUID,

    @NotBlank
    @Column(nullable = false)
    var code: String,
)
