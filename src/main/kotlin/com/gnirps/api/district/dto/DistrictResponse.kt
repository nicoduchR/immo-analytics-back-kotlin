package com.gnirps.api.district.dto

import java.util.*

/**
 * The District Response fields
 * @property id [UUID]
 * @property name [String]
 * @property code [String]
 */
data class DistrictResponse(
    val id: UUID,
    val name: String,
    val code: String,
)
