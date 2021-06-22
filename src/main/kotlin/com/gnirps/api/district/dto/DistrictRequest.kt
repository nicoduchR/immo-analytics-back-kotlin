package com.gnirps.api.district.dto

import javax.validation.constraints.NotEmpty

data class DistrictRequest(
    @NotEmpty val name: String,
    @NotEmpty val code: String
)
