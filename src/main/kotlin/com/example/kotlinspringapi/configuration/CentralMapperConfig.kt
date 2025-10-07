package com.example.kotlinspringapi.configuration

import org.mapstruct.MapperConfig
import org.mapstruct.MappingInheritanceStrategy
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy

@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
)
interface CentralMapperConfig