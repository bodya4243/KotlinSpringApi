package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@RestController
@RequestMapping("api/courses")
@Tag(name = "Courses", description = "API для роботи з курсами")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Додати новий курс", description = "Створює новий курс та повертає його")
    fun addCourse(@Valid @RequestBody courseDto: CourseDto) : CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    @Operation(summary = "Отримати всі курси", description = "Повертає список курсів, можна фільтрувати за назвою")
    fun getCourses(
        @Parameter(description = "Назва курсу для фільтрації", required = false)
        @RequestParam("course_name") courseName: String?
    ): List<CourseDto> {
        return courseService.retrieveAllCourses(courseName)
    }

    @PutMapping
    @Operation(summary = "Оновити курс", description = "Оновлює існуючий курс за ID")
    fun updateCourse(@Valid @RequestBody courseDto: CourseDto) : CourseDto {
        return courseService.updateCourse(courseDto)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити курс", description = "Видаляє курс за ID та повертає видалений об'єкт")
    fun deleteCourse(
        @Parameter(description = "ID курсу для видалення")
        @PathVariable("id") id: Int
    ): CourseDto {
        return courseService.deleteCourse(id)
    }
}
