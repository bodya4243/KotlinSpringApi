package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.service.InstructorServiceImpl
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Instructor", description = "API for instructors")
@RestController
@RequestMapping("api/instructors")
class InstructorController(val instructorService: InstructorServiceImpl) {

    @Operation(summary = "Create a new instructor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@Valid @RequestBody instructorDTO: InstructorDto): InstructorDto =
        instructorService.addNewInstructor(instructorDTO)
}