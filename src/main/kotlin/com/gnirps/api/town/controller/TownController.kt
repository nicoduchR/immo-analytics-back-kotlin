package com.gnirps.api.town.controller

import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.district.dto.DistrictResponse
import com.gnirps.api.district.mapper.DistrictMapper
import com.gnirps.api.district.model.District
import com.gnirps.api.district.service.DistrictService
import com.gnirps.api.town.dto.TownResponse
import com.gnirps.api.town.mapper.TownMapper
import com.gnirps.api.town.service.TownService
import com.gnirps.jwt.annotations.AdminAccess
import com.gnirps.logging.service.Logger
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@Api(
    tags = ["Town Controller"],
    value = "Town Controller",
    description = "Handle towns management."
)
@RestController
@RequestMapping(TownController.ROOT_PATH)
class TownController(
    private val townService: TownService,
    private val logger: Logger
) {
    companion object {
        const val ROOT_PATH: String = "/api/${ProjectProperties.VERSION}/towns"
    }

    /**
     * Finds a town by its ID
     * @param id
     * @return [TownResponse]
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a town.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Town retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "Town not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): TownResponse {
        return TownMapper.toResponse(townService.findById(id))
    }


    /**
     * Finds a town by its code
     * @param code
     * @return [TownResponse]
     */
    @GetMapping("/code/{code}")
    @ApiOperation(value = "Retrieve a town.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Town retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "Town not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findByCode(@PathVariable code: String): TownResponse {
        return TownMapper.toResponse(townService.findByCode(code))
    }

    /**
     * Get all towns
     * @return A list of [TownResponse]
     */
    @GetMapping
    @ApiOperation(value = "Retrieve all towns.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Towns retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<TownResponse> {
        return townService.findAll().map { TownMapper.toResponse(it) }
    }

    /**
     * Refresh list of  [Town]
     * @return A list of [TownResponse]
     */
    @GetMapping("/refresh")
    @ApiOperation(value = "Refresh list of Towns", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Towns retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden")
    )
    @ResponseStatus(HttpStatus.OK)
    fun refreshAll(): List<TownResponse> {
        return townService.retrieveTowns().map { TownMapper.toResponse(it) }
    }
}
