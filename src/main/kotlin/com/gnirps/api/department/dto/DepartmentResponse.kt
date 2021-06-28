package com.gnirps.api.department.dto

import com.gnirps.api.district.model.District
import java.util.*

/**
 * The Department Response fields
 * @property id [UUID]
 * @property name [String]
 * @property code [String]
 * @property district [District]
 */
data class DepartmentResponse(
    val id: UUID,
    val name: String,
    val code: String,
    val district: District,
)
