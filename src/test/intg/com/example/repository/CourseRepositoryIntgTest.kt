package com.kotlinspring.repository

import com.example.kotlinspringapi.KotlinSpringApiApplication
import com.example.kotlinspringapi.repository.CourseRepository
import com.example.kotlinspringapi.repository.InstructorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import util.PostgresSQLContainerInitializer
import util.courseEntityList
import util.instructorEntity
import java.util.stream.Stream

@SpringBootTest(
    classes = [KotlinSpringApiApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
class CourseRepositoryIntgTest : PostgresSQLContainerInitializer() {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp(){
        courseRepository.deleteAll()
        instructorRepository.deleteAll()

        val instructor = instructorEntity()
        instructorRepository.save(instructor)

        val courses = courseEntityList(instructor)
        courses.forEach {
            courseRepository.save(it)
        }
    }

    @Test
    fun findByNameContaining() {

        val courses = courseRepository.findByNameContaining("SpringBoot")

        println("courses  : $courses")
        assertEquals(2, courses.size)
    }

    @Test
    fun findCoursesByName() {

        val courses = courseRepository.findCoursesByName("SpringBoot")

        println("courses  : $courses")
        assertEquals(2, courses.size)
    }


    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(name: String, expectedSize:  Int) {

        val courses = courseRepository.findCoursesByName(name)

        println("courses  : $courses")
        assertEquals(expectedSize, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(Arguments.arguments("SpringBoot", 2),
                Arguments.arguments("Wiremock", 1))

        }
    }
}