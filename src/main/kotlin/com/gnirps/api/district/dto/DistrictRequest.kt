package com.gnirps.api.district.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

/**
 * The District Request fields
 * @property name [String]
 * @property code [String]
 */
data class DistrictRequest(

    @JsonProperty("nom")
    @NotEmpty val name: String,

    @JsonProperty("code")
    @NotEmpty val code: String
)
