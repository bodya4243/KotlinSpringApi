package com.example.kotlinspringapi.service

import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.exception.CourseNotFoundException
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl(val courseRepository: CourseRepository) : CourseService{
    override fun retrieveCourse(): List<CourseDto> {
        return courseRepository.findAll()
            .map {
                course -> (
                    CourseDto(
                    id = course.id,
                    name = course.name,
                    category = course.category
                ))
            }
    }

    override fun addCourse(course: CourseDto): CourseDto {
        val courseEntity = course.let {
            Course(null, it.name, it.category)
        }
        courseRepository.save(courseEntity)

        return courseEntity.let {
            CourseDto(it.id, it.name, it.category)
        }
    }

    override fun updateCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseRepository.findById(courseDto.id!!.toLong())

        return if (courseEntity.isPresent) {
           courseEntity.get().let {
               it.name = courseDto.name
               it.category = courseDto.category
               courseRepository.save(it)
               CourseDto(it.id, it.name, it.category)
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
                CourseDto(it.id, it.name, it.category)
            }
        } else {
            throw CourseNotFoundException("course not found by id: $id")
        }
    }
}