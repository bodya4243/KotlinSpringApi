package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.CourseDto

interface CourseService {
    fun retrieveAllCourses(courseName: String?): List<CourseDto>
    fun addCourse(course: CourseDto): CourseDto
    fun updateCourse(courseDto: CourseDto): CourseDto
    fun deleteCourse(id: Int): CourseDto
}