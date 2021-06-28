package com.gnirps.api.town.mapper

import com.gnirps.api.department.model.Department
import com.gnirps.api.district.model.District
import com.gnirps.api.town.dto.TownRequest
import com.gnirps.api.town.dto.TownResponse
import com.gnirps.api.town.model.Town
import java.util.*

class TownMapper {
    companion object {
        fun fromRequest(townRequest: TownRequest, department: Department, district: District, id: UUID? = null): Town {
            return Town(
                id = id ?: UUID.randomUUID(),
                name = townRequest.name,
                code = townRequest.code,
                zipCodes = townRequest.zipCodes,
                department = department,
                district = district,
                population = townRequest.population
            )
        }
        fun toResponse(town: Town): TownResponse =
            TownResponse(
                id = town.id,
                name = town.name,
                code = town.code,
                zipCodes = town.zipCodes,
                department = town.department,
                district = town.district,
                population = town.population
            )
    }
}
