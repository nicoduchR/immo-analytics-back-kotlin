package com.gnirps.api.department.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

/**
 * The Department Request fields
 * @property name [String]
 * @property code [String]
 * @property districtCode [String]
 */
data class DepartmentRequest(
    @JsonProperty("nom")
    @NotEmpty val name: String,

    @JsonProperty("code")
    @NotEmpty val code: String,

    @JsonProperty("codeRegion")
    @NotEmpty val districtCode: String
)
