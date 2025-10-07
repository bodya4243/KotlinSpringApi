package com.example.kotlinspringapi.configuration

import com.example.kotlinspringapi.converter.EntityToDtoConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(EntityToDtoConverter)
    }
}