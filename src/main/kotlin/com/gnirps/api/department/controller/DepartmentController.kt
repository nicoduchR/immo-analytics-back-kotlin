package com.gnirps.api.department.controller

import com.gnirps.api.config.properties.ProjectProperties
import com.gnirps.api.department.dto.DepartmentRequest
import com.gnirps.api.department.dto.DepartmentResponse
import com.gnirps.api.department.mapper.DepartmentMapper
import com.gnirps.api.department.model.Department
import com.gnirps.api.department.service.DepartmentService
import com.gnirps.api.district.dto.DistrictRequest
import com.gnirps.api.district.dto.DistrictResponse
import com.gnirps.api.district.mapper.DistrictMapper
import com.gnirps.api.district.model.District
import com.gnirps.api.district.service.DistrictService
import com.gnirps.jwt.annotations.AdminAccess
import com.gnirps.jwt.config.SecurityConstants
import com.gnirps.logging.service.Logger
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@Api(
    tags = ["Department Controller"],
    value = "Department Controller",
    description = "Handle departments management."
)
@RestController
@RequestMapping(DepartmentController.ROOT_PATH)
class DepartmentController(
    private val departmentService: DepartmentService,
    private val districtService: DistrictService,
    private val logger: Logger
) {
    companion object {
        const val ROOT_PATH: String = "/api/${ProjectProperties.VERSION}/departments"
    }

    /**
     * Finds a department by its ID
     * @param id
     * @return [DepartmentResponse]
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieve a department.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Department retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "Department not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): DepartmentResponse {
        return DepartmentMapper.toResponse(departmentService.findById(id))
    }

    /**
     * Finds a department by its code
     * @param code
     * @return [DepartmentResponse]
     */
    @GetMapping("/code/{code}")
    @ApiOperation(value = "Retrieve a department.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Department retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden"),
        ApiResponse(code = 404, message = "Department not found"),
        ApiResponse(code = 413, message = "Payload too large")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findByCode(@PathVariable code: String): DepartmentResponse {
        return DepartmentMapper.toResponse(departmentService.findByCode(code))
    }

    /**
     * Get all departments
     * @return A list of [DepartmentResponse]
     */
    @GetMapping
    @ApiOperation(value = "Retrieve all departments.", authorizations = [Authorization(value = "Bearer")])
    @AdminAccess
    @ApiResponses(
        ApiResponse(code = 200, message = "Departments retrieved"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 403, message = "Forbidden")
    )
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<DepartmentResponse> {
        return departmentService.findAll().map { DepartmentMapper.toResponse(it) }
    }
}
