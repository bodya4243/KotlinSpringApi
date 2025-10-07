package com.example.kotlinspringapi.converter

import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.model.Instructor
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class InstructorToDtoConverter : Converter<Instructor, InstructorDto> {
    override fun convert(source: Instructor): InstructorDto? {
        return InstructorDto(id = source.id, name = source.name)
    }
}