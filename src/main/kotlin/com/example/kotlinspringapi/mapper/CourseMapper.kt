package com.example.kotlinspringapi.mapper

import com.example.kotlinspringapi.configuration.CentralMapperConfig
import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import org.mapstruct.Mapper

@Mapper(config = CentralMapperConfig::class)
interface CourseMapper {
    fun toDto(course: Course) : CourseDto
    fun toModel(courseDto: CourseDto) : Course
}