package com.gnirps.api.town.repository

import com.gnirps.api.town.model.Town
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TownRepository : JpaRepository<Town, UUID> {
    fun getDistinctByCode(code: String): Town

    fun existsByCode(code: String): Boolean
}
