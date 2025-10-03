package com.example.kotlinspringapi.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDto(
    val id: Int?,
    @get:NotBlank(message = "name is required")
    val name: String
)