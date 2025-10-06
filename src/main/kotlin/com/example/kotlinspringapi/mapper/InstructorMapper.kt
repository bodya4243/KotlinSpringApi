package com.example.kotlinspringapi.mapper

import com.example.kotlinspringapi.configuration.CentralMapperConfig
import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.model.Instructor
import org.mapstruct.Mapper

@Mapper(config = CentralMapperConfig::class)
interface InstructorMapper {
    fun toDto(instructor: Instructor) : InstructorDto
    fun toModel(instructorDto: InstructorMapper) : Instructor
}