package com.example.kotlinspringapi.converter

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair

object EntityToDtoConverter : GenericConverter {
    override fun getConvertibleTypes(): Set<GenericConverter.ConvertiblePair?>? {

        val pairs: Set<GenericConverter.ConvertiblePair> = setOf(
            ConvertiblePair(CourseDto::class.java, Course::class.java),
            ConvertiblePair(Course::class.java, CourseDto::class.java)
        )

        return pairs
    }

    override fun convert(
        source: Any?,
        sourceType: TypeDescriptor,
        targetType: TypeDescriptor
    ): Any? {
        return when (sourceType.type) {
            Course::class.java -> {
                val course = source as? Course ?: return null
                CourseDto(course.id, course.name, course.category, course.instructor?.id)
            }

            CourseDto::class.java -> {
                val dto = source as? CourseDto ?: return null
                Course(dto.id, dto.name, dto.category)
            }

            else -> null
        }
    }
}