package com.gnirps.api.department.repository

import com.gnirps.api.department.model.Department
import com.gnirps.api.district.model.District
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DepartmentRepository : JpaRepository<Department, UUID> {
    fun getDistinctByCode(code: String): Department

    fun existsByName(name: String): Boolean
}
