package com.example.kotlinspringapi.repository

import com.example.kotlinspringapi.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c JOIN FETCH c.instructor WHERE c.name LIKE %:name%")
    fun findByNameContaining(@Param("name") name: String): List<Course>

    @Query("SELECT c FROM Course c JOIN FETCH c.instructor WHERE c.name LIKE %:name%")
    fun findCoursesByName(@Param("name") courseName: String): List<Course>
}