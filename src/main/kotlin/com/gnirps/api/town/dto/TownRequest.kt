package com.gnirps.api.town.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * The Town Request fields
 * @property name [String]
 * @property code [String]
 * @property zipCodes [List<String>]
 * @property departmentCode [String]
 * @property districtCode [String]
 * @property population [Int]
 */
data class TownRequest(
    @JsonProperty("nom")
    @NotEmpty val name: String,

    @JsonProperty("code")
    @NotEmpty val code: String,

    @JsonProperty("codesPostaux")
    val zipCodes: List<String>,

    @JsonProperty("codeDepartement")
    val departmentCode: String = "",

    @JsonProperty("codeRegion")
    val districtCode: String = "",

    @JsonProperty("population")
    val population: String = ""
)
