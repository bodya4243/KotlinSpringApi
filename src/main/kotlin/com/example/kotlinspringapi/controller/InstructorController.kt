package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.service.InstructorServiceImpl
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/instructors")
class InstructorController(val instructorService: InstructorServiceImpl) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInstructor(@Valid @RequestBody instructorDTO: InstructorDto): InstructorDto = instructorService.addNewInstructor(instructorDTO)
}