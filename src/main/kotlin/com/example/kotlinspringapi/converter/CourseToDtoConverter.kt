package com.example.kotlinspringapi.converter

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component

@Component
class CourseToDtoConverter : Converter<Course, CourseDto> {
    override fun convert(source: Course): CourseDto? {
        return CourseDto(
            id = source.id,
            name = source.name,
            category = source.category,
            instructorId = source.instructor?.id
        )
    }
}