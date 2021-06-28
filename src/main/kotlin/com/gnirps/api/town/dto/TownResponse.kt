package com.gnirps.api.town.dto

import com.gnirps.api.department.model.Department
import com.gnirps.api.district.model.District
import java.util.*

/**
 * The Town Response fields
 * @property id [UUID]
 * @property name [String]
 * @property code [String]
 * @property zipCodes [List<String>]
 * @property department [Department]
 * @property district [District]
 * @property population [Int]
 */
data class TownResponse(
    val id: UUID,
    val name: String,
    val code: String,
    val zipCodes: List<String>,
    val department: Department,
    val district: District,
    val population: String
)
