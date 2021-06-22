package com.gnirps.api.district.controller

import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.district.service.DistrictService
import com.gnirps.api.district.dto.*
import com.gnirps.api.district.mapper.DistrictMapper
import com.gnirps.api.district.model.District
import com.gnirps.logging.service.Logger
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.ws.rs.BadRequestException


@Api(
    tags = ["District Controller"],
    value = "District Controller",
    description = "Handle districts management."
)
@RestController
@RequestMapping(DistrictController.ROOT_PATH)
class DistrictController(
    private val districtService: DistrictService,
    private val logger: Logger
) {
    companion object {
        const val ROOT_PATH: String = "/api/${ProjectProperties.VERSION}/districts"
    }

    /**
     * Create a new district
     * @return [DistrictResponse]
     */
    @PostMapping
    @ApiOperation(value = "Store a new district.")
    @ApiResponses(
        ApiResponse(code = 201, message = "District created"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 409, message = "District already exist")
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody districtRequest: DistrictRequest): DistrictResponse {
        val district: District = districtService.create(DistrictMapper.fromRequest(districtRequest))
        logger.info("$district created", Logger.EventType.OPERATION)
        return DistrictMapper.toResponse(district)
    }

    /**
     * Finds a district by its ID
     * @param id
     * @return [DistrictResponse]
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a district.")
    @ApiResponses(
        ApiResponse(code = 200, message = "District retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "District not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): DistrictResponse {
        return DistrictMapper.toResponse(districtService.findById(id))
    }


    /**
     * Finds a district by its code
     * @param code
     * @return [DistrictResponse]
     */
    @GetMapping("/code/{code}")
    @ApiOperation(value = "Retrieve a district.")
    @ApiResponses(
        ApiResponse(code = 200, message = "District retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "District not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findByCode(@PathVariable code: String): DistrictResponse {
        return DistrictMapper.toResponse(districtService.findByCode(code))
    }

    /**
     * Get all districts
     * @return A list of [DistrictResponse]
     */
    @GetMapping
    @ApiOperation(value = "Retrieve all districts.")
    @ApiResponses(
        ApiResponse(code = 200, message = "Districts retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<DistrictResponse> {
        return districtService.findAll().map { DistrictMapper.toResponse(it) }
    }

    /**
     * Updates a district
     * @param id
     * @param districtRequest [DistrictRequest]
     * @return [DistrictResponse]
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a district.")
    @ApiResponses(
        ApiResponse(code = 200, message = "District updated"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "District not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun updateById(@PathVariable id: UUID, @RequestBody districtRequest: DistrictRequest): DistrictResponse {
        logger.info("district $id updated", Logger.EventType.OPERATION)
        return DistrictMapper.toResponse(districtService.update(DistrictMapper.fromRequest(districtRequest, id)))
    }

    /**
     * Deletes a district
     * @param id [UUID]
     * @return [DistrictResponse]
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a district.")
    @ApiResponses(
        ApiResponse(code = 204, message = "District deleted"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "District not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: UUID): DistrictResponse {
        logger.info("district $id removed", Logger.EventType.OPERATION)
        return DistrictMapper.toResponse(districtService.deleteById(id))
    }
}
