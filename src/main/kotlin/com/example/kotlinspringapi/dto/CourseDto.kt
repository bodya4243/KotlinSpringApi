package com.example.kotlinspringapi.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDto (
    val id: Int?,

    @field:NotBlank(message = "Name is required")
    @get:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "category is required")
    @get:NotBlank(message = "category is required")
    val category: String,

    @get:NotNull(message = "instructor is required")
    @field:NotNull(message = "instructor is required")
    val instructorId: Int? = null
)