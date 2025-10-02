package com.example.kotlinspringapi.repository

import com.example.kotlinspringapi.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Long>