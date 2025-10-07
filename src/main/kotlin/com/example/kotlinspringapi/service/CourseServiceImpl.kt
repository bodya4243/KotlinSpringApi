package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.exception.CourseNotFoundException
import com.example.kotlinspringapi.exception.InstructorNotValidException
import com.example.kotlinspringapi.mapper.CourseMapper
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val instructorService: InstructorServiceImpl,
    val courseMapper: CourseMapper
) : CourseService{
    override fun retrieveAllCourses(courseName: String?): List<CourseDto> {

        val courses = courseName?.let {
            courseRepository.findByNameContaining(it)
        } ?: courseRepository.findAll()

        return courses.map { courseMapper.toDto(it) }
    }

    override fun addCourse(course: CourseDto): CourseDto {
        val instructor = instructorService.findInstructorById(course.instructorId!!)

        if(!instructor.isPresent){
            throw InstructorNotValidException("Instructor Id is not Valid!")
        }

        val courseEntity = course.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        return courseEntity.let {
            courseMapper.toDto(it)
        }
    }

    override fun updateCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseRepository.findById(courseDto.id!!.toLong())

        return if (courseEntity.isPresent) {
           courseEntity.get().let {
               it.name = courseDto.name
               it.category = courseDto.category
               courseRepository.save(it)
               courseMapper.toDto(it)
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
                courseMapper.toDto(it)
            }
        } else {
            throw CourseNotFoundException("course not found by id: $id")
        }
    }
}