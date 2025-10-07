package com.example.kotlinspringapi.converter

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class DtoToCourseConverter : Converter<CourseDto, Course> {
    override fun convert(source: CourseDto): Course? {
        return Course(id = source.id, name = source.name, category = source.category)
    }
}