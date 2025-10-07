package com.example.kotlinspringapi.config

import com.example.kotlinspringapi.converter.CourseToDtoConverter
import com.example.kotlinspringapi.converter.DtoToCourseConverter
import com.example.kotlinspringapi.converter.InstructorToDtoConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(CourseToDtoConverter())
        registry.addConverter(DtoToCourseConverter())
        registry.addConverter(InstructorToDtoConverter())
    }
}