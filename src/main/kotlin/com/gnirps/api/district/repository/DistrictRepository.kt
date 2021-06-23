package com.gnirps.api.district.repository

import com.gnirps.api.district.model.District
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DistrictRepository : JpaRepository<District, UUID> {
    fun getDistinctByCode(code: String): District

    fun existsByName(name: String): Boolean

}
