package com.gnirps.api.district.service

import com.gnirps.api.district.model.District
import com.gnirps.api.district.repository.DistrictRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.ws.rs.BadRequestException


@Service
class DistrictService(
    private val districtRepository: DistrictRepository
) {

    /**
     * Create a [District]
     * @param district [District]
     * @return The created [District] object
     */
    fun create(district: District): District {
        return districtRepository.saveAndFlush(district)
    }
    /**
     * Find all [District]
     * @return All found [District]
     */
    fun findAll(): List<District> {
        return districtRepository.findAll()
    }

    /**
     * Find a [District] by its [District.id]
     * @param id [UUID]
     * @return The found [District]
     */
    fun findById(id: UUID): District {
        return districtRepository.findById(id).orElseThrow { EntityNotFoundException("district $id not found") }
    }

    /**
     * Find a district by given [District.code]
     * @param code [String]
     * @return The [District] associated with the given code
     */
    fun findByCode(code: String): District {
        return districtRepository.getDistinctByCode(code)
    }

    /**
     * Update a [District]
     * @param district [District]
     * @return The updated [District]
     */
    fun update(district: District): District {
        if (district.name.isBlank() || district.code.isBlank()) {
            throw BadRequestException("Invalid request")
        }
        return districtRepository.saveAndFlush(district)
    }

    /**
     * Delete a [District] by its [District.id]
     * @param id [UUID]
     * @return The deleted [District]
     */
    fun deleteById(id: UUID): District {
        val district = districtRepository.findById(id).orElseThrow { EntityNotFoundException("district $id not found") }
        districtRepository.deleteById(id)
        return district
    }
}
