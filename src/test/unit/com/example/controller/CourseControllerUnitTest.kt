package com.example.controller

import com.example.kotlinspringapi.KotlinSpringApiApplication
import com.example.kotlinspringapi.dto.CourseDto
import com.example.kotlinspringapi.model.Course
import com.example.kotlinspringapi.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import util.PostgresSQLContainerInitializer
import util.courseDTO

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinSpringApiApplication::class]
)
@AutoConfigureWebTestClient(timeout = "6000000")
class CourseControllerUnitTest : PostgresSQLContainerInitializer() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun `addCourse - should return created course`() {
        val courseDTO = courseDTO()
        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient.post()
            .uri("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(savedCourseDTO?.id)
    }



    @Test
    fun `addCourse - validation failure`() {
        val courseDTO = courseDTO(name = "", category = "")

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val response = webTestClient.post()
            .uri("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(
            "[\"Name is required\",\"category is required\"]",
            response
        )
    }

    @Test
    fun `addCourse - runtime exception`() {
        val courseDTO = courseDTO()
        val errorMessage = "Unexpected Error Occurred!"
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient.post()
            .uri("api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }

    @Test
    fun `retrieveAllCourses - should return list of courses`() {
        every { courseServiceMock.retrieveAllCourses(any()) } returns listOf(
            CourseDto(1, "Build RestFul APis using Spring Boot and Kotlin", "Development", 1),
            CourseDto(2, "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development", 1)
        )

        val courseDTOs = webTestClient.get()
            .uri("api/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(2, courseDTOs!!.size)
    }

    @Test
    fun `updateCourse - should return updated course`() {
        val updatedCourseDto = CourseDto(null, "Apache Kafka for Developers using Spring Boot1", "Development")
        every { courseServiceMock.updateCourse(any()) } returns CourseDto(
            100,
            "Apache Kafka for Developers using Spring Boot1",
            "Development",
            1
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

        assertEquals("Apache Kafka for Developers using Spring Boot1", updatedCourseDto.name)
    }

    @Test
    fun `deleteCourse - should delete course`() {
        val deletedCourse = courseDTO(id = 100)
        coEvery { courseServiceMock.deleteCourse(any()) } returns deletedCourse

        webTestClient.delete()
            .uri("/api/courses/{id}", 100)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        verify(exactly = 1) { courseServiceMock.deleteCourse(any()) }
    }
}
