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
}
