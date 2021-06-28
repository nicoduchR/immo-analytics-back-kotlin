package com.gnirps.api.department.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.department.dto.DepartmentRequest
import com.gnirps.api.department.mapper.DepartmentMapper
import com.gnirps.api.department.model.Department
import com.gnirps.api.department.repository.DepartmentRepository
import com.gnirps.api.district.dto.DistrictRequest
import com.gnirps.api.district.mapper.DistrictMapper
import com.gnirps.api.district.model.District
import com.gnirps.api.district.repository.DistrictRepository
import com.gnirps.api.district.service.DistrictService
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
class DepartmentService(
    private val departmentRepository: DepartmentRepository,
    private val districtService: DistrictService,
    restTemplateBuilder: RestTemplateBuilder,
) {

    /**
     * The rest template
     */
    private val restTemplate = restTemplateBuilder.build()

    /**
     * Update a [Department]
     * @return updated [Department]
     */
    fun update(department: Department): Department {
        return departmentRepository.saveAndFlush(department)
    }


    /**
     * Find all [Department]
     * @return All found [Department]
     */
    fun findAll(): List<Department> {
        return departmentRepository.findAll()
    }

    /**
     * Find one [Department] with status processed at false
     * @return One [Department] or null
     */
    fun findOneNotProcessed(): Department? {
        return departmentRepository.getFirstByProcessedFalse()
    }

    /**
     * Reset all [Department] to status processed false
     */
    fun resetAllDepartmentToStatusNotProcessed() {
        val departments: List<Department> = departmentRepository.findAll()
        departments.forEach {
            it.processed = false
            departmentRepository.saveAndFlush(it)
        }
    }

    /**
     * Find a [Department] by its [Department.id]
     * @param id [UUID]
     * @return The found [Department]
     */
    fun findById(id: UUID): Department {
        return departmentRepository.findById(id).orElseThrow { EntityNotFoundException("department $id not found") }
    }

    /**
     * Find a department by given [Department.code]
     * @param code [String]
     * @return The [Department] associated with the given code
     */
    fun findByCode(code: String): Department {
        return departmentRepository.getDistinctByCode(code)
    }

    /**
     * Retrieve all [Department] from the government api and store them in database every day at 00:01
     * @return the list of [Department] objects
     */
    @Scheduled(cron = "0 1 0 *  * ?")
    fun retrieveDepartments(): List<Department> {

        val headers = HttpHeaders()
        headers.add("content-type", "application/json")
        val request: HttpEntity<String> = HttpEntity<String>(headers)


        val response: ResponseEntity<String> = restTemplate.exchange(
            ProjectProperties.DEPARTMENT_API_URL,
            HttpMethod.GET,
            request,
            String::class.java
        )

        // Decode JSON response
        val mapper = ObjectMapper()
        val departmentsRequests: Array<DepartmentRequest> = mapper.readValue(response.body, Array<DepartmentRequest>::class.java)


        val departments : List<Department> = departmentsRequests.map {
            val district = districtService.findByCode(it.districtCode)
            DepartmentMapper.fromRequest(it, district)
        }

        val newDepartments = mutableListOf<Department>()

        departments.forEach {
            if (!departmentRepository.existsByCode(it.code)){
                newDepartments.add(it)
                departmentRepository.saveAndFlush(it)
            }
        }

        return newDepartments
    }
}
