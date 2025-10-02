package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/course")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDto: CourseDto) : CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun getCourses(): List<CourseDto> {
        return courseService.retrieveCourse()
    }

    @PutMapping
    fun updateCourse(@RequestBody courseDto: CourseDto) : CourseDto {
        return courseService.updateCourse(courseDto)
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): CourseDto {
        return courseService.deleteCourse(id)
    }
}