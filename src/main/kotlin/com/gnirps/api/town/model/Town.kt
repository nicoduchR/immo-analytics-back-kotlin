package com.gnirps.api.town.model

import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.department.model.Department
import com.gnirps.api.district.model.District
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Entity
data class Town(
    @Id
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = ProjectProperties.UUID_SIZE)
    val id: UUID,

    @Column(unique=true)
    @NotEmpty var name: String,

    @NotEmpty
    @Column(unique=true)
    val code: String,

    @NotEmpty
    @ElementCollection
    val zipCodes: List<String>,

    @NotNull
    @ManyToOne(cascade = [CascadeType.DETACH], optional = false)
    var department: Department,

    @NotNull
    @ManyToOne(cascade = [CascadeType.DETACH], optional = false)
    var district: District,

    @NotEmpty
    @Column(unique=true)
    val population: String,
)

