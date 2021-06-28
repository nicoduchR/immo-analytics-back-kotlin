package com.gnirps.api.district.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.district.dto.DistrictRequest
import com.gnirps.api.district.dto.DistrictResponse
import com.gnirps.api.district.mapper.DistrictMapper
import com.gnirps.api.district.model.District
import com.gnirps.api.district.repository.DistrictRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.ws.rs.BadRequestException


@Service
@EnableScheduling
class DistrictService(
    private val districtRepository: DistrictRepository,
    restTemplateBuilder: RestTemplateBuilder,
) {

    /**
     * The rest template
     */
    private val restTemplate = restTemplateBuilder.build()

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
     * Retrieve all [District] from the government api and store them in database every day at midnight
     * @return the list of [District] objects
     */
    @Scheduled(cron = "0 0 0 * * ?")
    fun retrieveDistricts(): List<District> {

        val headers = HttpHeaders()
        headers.add("content-type", "application/json")
        val request: HttpEntity<String> = HttpEntity<String>(headers)


        val response: ResponseEntity<String> = restTemplate.exchange(
            ProjectProperties.DISTRICT_API_URL,
            HttpMethod.GET,
            request,
            String::class.java
        )

        // Decode JSON response
        val mapper = ObjectMapper()
        val districtsRequests: Array<DistrictRequest> = mapper.readValue(response.body, Array<DistrictRequest>::class.java)

        val districts : List<District> = districtsRequests.map { DistrictMapper.fromRequest(it) }

        val newDistricts = mutableListOf<District>()

        districts.forEach {
            if (!districtRepository.existsByCode(it.code)){
                newDistricts.add(it)
                districtRepository.saveAndFlush(it)
            }
        }

        return newDistricts
    }
}
