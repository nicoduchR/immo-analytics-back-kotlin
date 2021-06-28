package com.gnirps.api.town.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.department.dto.DepartmentRequest
import com.gnirps.api.department.mapper.DepartmentMapper
import com.gnirps.api.department.model.Department
import com.gnirps.api.department.repository.DepartmentRepository
import com.gnirps.api.department.service.DepartmentService
import com.gnirps.api.district.service.DistrictService
import com.gnirps.api.town.dto.TownRequest
import com.gnirps.api.town.mapper.TownMapper
import com.gnirps.api.town.model.Town
import com.gnirps.api.town.repository.TownRepository
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


@Service
@EnableScheduling
class TownService(
    private val townRepository: TownRepository,
    private val departmentService: DepartmentService,
    private val districtService: DistrictService,
    restTemplateBuilder: RestTemplateBuilder,
) {

    /**
     * The rest template
     */
    private val restTemplate = restTemplateBuilder.build()

    /**
     * Find all [Town]
     * @return All found [Town]
     */
    fun findAll(): List<Town> {
        return townRepository.findAll()
    }

    /**
     * Find a [Town] by its [Town.id]
     * @param id [UUID]
     * @return The found [Town]
     */
    fun findById(id: UUID): Town {
        return townRepository.findById(id).orElseThrow { EntityNotFoundException("town $id not found") }
    }

    /**
     * Find a town by given [Town.code]
     * @param code [String]
     * @return The [Town] associated with the given code
     */
    fun findByCode(code: String): Town {
        return townRepository.getDistinctByCode(code)
    }

    /**
     * Retrieve all [Town] from the government api and store them in database every day at 00:02
     * @return the list of [Town] objects
     */
    @Scheduled(cron = "0 */2 * ? * *") // 0 2 0 *  * ?
    fun retrieveTowns(): List<Town> {

        print("Fetching towns ...\n")

        val newTowns = mutableListOf<Town>()
        val department : Department? = departmentService.findOneNotProcessed()

        if(department == null){
            departmentService.resetAllDepartmentToStatusNotProcessed()
        }else {

            department.processed = true
            departmentService.update(department)

            val headers = HttpHeaders()
            headers.add("content-type", "application/json")
            val request: HttpEntity<String> = HttpEntity<String>(headers)


            val response: ResponseEntity<String> = restTemplate.exchange(
                "${ProjectProperties.TOWN_API_URL_BASE_URL}/${department.code}/communes",
                HttpMethod.GET,
                request,
                String::class.java
            )

            // Decode JSON response
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val townsRequests: Array<TownRequest> = mapper.readValue(response.body, Array<TownRequest>::class.java)


            val towns : List<Town> = townsRequests.map {
                val department = departmentService.findByCode(it.departmentCode)
                val district = districtService.findByCode(it.districtCode)
                TownMapper.fromRequest(it, department, district)
            }



            towns.forEach {
                if (!townRepository.existsByCode(it.code)){
                    newTowns.add(it)
                    townRepository.saveAndFlush(it)
                }
            }


        }

        return newTowns
    }
}
