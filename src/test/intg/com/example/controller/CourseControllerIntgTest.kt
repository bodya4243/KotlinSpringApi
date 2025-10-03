package com.kotlinspring.controller

import com.example.kotlinspringapi.KotlinSpringApiApplication
import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.repository.CourseRepository
import com.example.kotlinspringapi.repository.InstructorRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import util.courseEntityList
import util.instructorEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinSpringApiApplication::class])
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Testcontainers
internal class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

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

    companion object {

        @Container
        val postgresDB = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine")).apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("secret")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresDB::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDB::getUsername)
            registry.add("spring.datasource.password", postgresDB::getPassword)
        }
    }

    @Test
    fun addCourse() {

        val instructor = instructorRepository.findInstructorByName("Dilip Sundarraj")

        val courseDto = CourseDto(null,
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj",
            instructor.id )

        val savedCourseDTO = webTestClient
            .post()
            .uri("api/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertTrue {
            savedCourseDTO!!.id!=null
        }
    }


    @Test
    fun addCourse_InvlaidOInstructorId() {

        //given
        val courseDTO = CourseDto(null,
            "Build RestFul APis using Spring Boot and Kotlin", "Dilip Sundarraj",
            999 )

        //when
        val response = webTestClient
            .post()
            .uri("api/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        //then
        assertEquals("Instructor Id is not Valid!", response)
    }

    @Test
    fun retrieveAllCourses() {

        val courseDTOs = webTestClient
            .get()
            .uri("api/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        assertEquals(3, courseDTOs!!.size)

    }

    @Test
    fun retrieveAllCourses_ByName() {

        val uri = UriComponentsBuilder.fromUriString("api/courses")
            .queryParam("course_name", "SpringBoot")
            .toUriString()

        val courseDTOs = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse() {
        val instructor = instructorRepository.findInstructorByName("Dilip Sundarraj")
        val courseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot",
            "Development",
            instructor
        )
        courseRepository.save(courseEntity)

        val updatedCourseDto = CourseDto(
            id = courseEntity.id,
            name = "Apache Kafka for Developers using Spring Boot1",
            category = "Development",
            instructorId = instructor.id
        )

        val responseDto = webTestClient
            .put()
            .uri("/api/courses")
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals("Apache Kafka for Developers using Spring Boot1", responseDto?.name)
    }

    @Test
    fun deleteCourse() {

        val instructor = instructorRepository.findInstructorByName("Dilip Sundarraj")
        val courseEntity = Course(null,
            "Apache Kafka for Developers using Spring Boot", "Development" ,
            instructor)

        courseRepository.save(courseEntity)
        webTestClient
            .delete()
            .uri("api/courses/{courseId}", courseEntity.id)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)

    }
}