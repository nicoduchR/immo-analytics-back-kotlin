package com.gnirps.api.department.mapper

import com.gnirps.api.department.dto.DepartmentRequest
import com.gnirps.api.department.dto.DepartmentResponse
import com.gnirps.api.department.model.Department
import com.gnirps.api.district.dto.DistrictRequest
import com.gnirps.api.district.dto.DistrictResponse
import com.gnirps.api.district.model.District
import java.util.*

class DepartmentMapper {
    companion object {
        fun fromRequest(departmentRequest: DepartmentRequest, district: District,  id: UUID? = null): Department {
            return Department(
                id = id ?: UUID.randomUUID(),
                name = departmentRequest.name,
                code = departmentRequest.code,
                district = district
            )
        }
        fun toResponse(department: Department): DepartmentResponse =
            DepartmentResponse(
                id = department.id,
                name = department.name,
                code = department.code,
                district = department.district
            )
    }
}
