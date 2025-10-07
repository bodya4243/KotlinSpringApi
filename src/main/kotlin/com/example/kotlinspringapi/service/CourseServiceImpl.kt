package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.converter.EntityToDtoConverter
import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.exception.CourseNotFoundException
import com.example.kotlinspringapi.exception.InstructorNotValidException
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.repository.CourseRepository
import org.springframework.core.convert.TypeDescriptor
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val instructorService: InstructorServiceImpl,
) : CourseService {
    override fun retrieveAllCourses(courseName: String?): List<CourseDto> {

        val courses = courseName?.let {
            courseRepository.findByNameContaining(it)
        } ?: courseRepository.findAll()

        return courses.map { course -> toCourseDto(course) }
    }

    override fun addCourse(course: CourseDto): CourseDto {
        val instructor = instructorService.findInstructorById(course.instructorId!!)

        if (!instructor.isPresent) {
            throw InstructorNotValidException("Instructor Id is not Valid!")
        }

        val courseEntity = course.let {
            Course(null, it.name, it.category, instructor.get())
        }
        courseRepository.save(courseEntity)

        return toCourseDto(courseEntity)
    }

    override fun updateCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseRepository.findById(courseDto.id!!.toLong())

        return if (courseEntity.isPresent) {
            courseEntity.get().let {
                it.name = courseDto.name
                it.category = courseDto.category
                courseRepository.save(it)
                toCourseDto(it)
            }
        } else {
            throw CourseNotFoundException("course not found by id: ${courseDto.id}")
        }
    }

    override fun deleteCourse(id: Int): CourseDto {

        val courseEntity = courseRepository.findById(id.toLong())

        return if (courseEntity.isPresent) {
            courseEntity.get().let {
                courseRepository.deleteById(it.id!!.toLong())
                toCourseDto(it)
            }
        } else {
            throw CourseNotFoundException("course not found by id: $id")
        }
    }

    private fun toCourseDto(course: Course) : CourseDto {
        return EntityToDtoConverter.convert(
            course,
            TypeDescriptor.valueOf(Course::class.java),
            TypeDescriptor.valueOf(CourseDto::class.java)
        ) as CourseDto
    }
}