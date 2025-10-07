package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.exception.CourseNotFoundException
import com.example.kotlinspringapi.exception.InstructorNotValidException
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.repository.CourseRepository
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val instructorService: InstructorServiceImpl,
    val converter: ConversionService
) : CourseService {
    override fun retrieveAllCourses(courseName: String?): List<CourseDto> {

        val courses = courseName?.let {
            courseRepository.findByNameContaining(it)
        } ?: courseRepository.findAll()

        return courses.mapNotNull {
            course -> converter.convert(course, CourseDto::class.java)
        }
    }

    override fun addCourse(courseDto: CourseDto): CourseDto {
        val instructor = instructorService.findInstructorById(courseDto.instructorId!!)

        if (!instructor.isPresent) {
            throw InstructorNotValidException("Instructor Id is not Valid!")
        }

        val courseEntity = converter.convert(courseDto, Course::class.java)!!

        courseRepository.save(courseEntity)

        return converter.convert(courseEntity, CourseDto::class.java)!!
    }

    override fun updateCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseRepository.findById(courseDto.id!!.toLong())

        return if (courseEntity.isPresent) {
            val entityToSave = converter.convert(courseDto, Course::class.java)!!
            converter.convert(courseRepository.save(entityToSave), CourseDto::class.java)!!
        } else {
            throw CourseNotFoundException("course not found by id: ${courseDto.id}")
        }
    }

    override fun deleteCourse(id: Int): CourseDto {

        val courseEntity = courseRepository.findById(id.toLong())

        return if (courseEntity.isPresent) {
            courseEntity.get().let {
                courseRepository.deleteById(it.id!!.toLong())
                converter.convert(it, CourseDto::class.java)!!
            }
        } else {
            throw CourseNotFoundException("course not found by id: $id")
        }
    }
}