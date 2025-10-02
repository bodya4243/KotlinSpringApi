package com.example.kotlinspringapi.dto

import jakarta.validation.constraints.NotBlank

data class CourseDto (
    val id: Int?,

    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "category is required")
    val category: String
)