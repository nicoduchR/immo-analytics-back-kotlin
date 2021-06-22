package com.gnirps.api.district.mapper

import com.gnirps.api.district.dto.DistrictRequest
import com.gnirps.api.district.dto.DistrictResponse
import com.gnirps.api.district.model.District
import java.util.*

class DistrictMapper {
    companion object {
        fun fromRequest(districtRequest: DistrictRequest,  id: UUID? = null): District {
            return District(
                id = id ?: UUID.randomUUID(),
                name = districtRequest.name,
                code = districtRequest.code
            )
        }
        fun toResponse(district: District): DistrictResponse =
            DistrictResponse(
                id = district.id,
                name = district.name,
                code = district.code
            )
    }
}
