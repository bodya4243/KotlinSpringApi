package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.model.Instructor
import com.example.kotlinspringapi.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorServiceImpl(val instructorRepository: InstructorRepository) : InstructorService {

    override fun addNewInstructor(instructorDTO: InstructorDto): InstructorDto {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }

        instructorRepository.save(instructorEntity)

        return instructorEntity.let {
            InstructorDto(it.id, it.name)
        }
    }

    override fun findInstructorById(instructorId: Int): Optional<Instructor> {

        return  instructorRepository.findById(instructorId)
    }
}