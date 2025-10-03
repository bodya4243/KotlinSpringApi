package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.model.Instructor
import java.util.Optional

interface InstructorService {
    fun addNewInstructor(instructorDTO: InstructorDto): InstructorDto
    fun findInstructorById(instructorId: Int): Optional<Instructor>
}