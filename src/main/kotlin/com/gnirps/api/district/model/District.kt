package com.gnirps.api.district.model

import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.department.model.Department
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
data class District(
    @Id
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = ProjectProperties.UUID_SIZE)
    val id: UUID,

    @Column(unique=true)
    @NotEmpty var name: String,

    @NotEmpty
    @Column(unique=true)
    val code: String,
)
