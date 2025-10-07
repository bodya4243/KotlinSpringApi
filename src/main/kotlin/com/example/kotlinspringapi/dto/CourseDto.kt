package com.example.kotlinspringapi.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDto(
    val id: Int? = null,

    @field:NotBlank(message = "Name is required")
    val name: String = "",

    @field:NotBlank(message = "Category is required")
    val category: String = "",

    @field:NotNull(message = "Instructor is required")
    val instructorId: Int? = null
)