package com.example.controller

import com.example.kotlinspringapi.KotlinSpringApiApplication
import com.example.kotlinspringapi.dto.InstructorDto
import com.example.kotlinspringapi.service.InstructorServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import util.PostgresSQLContainerInitializer
import util.instructorDTO

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinSpringApiApplication::class]
)
@AutoConfigureWebTestClient(timeout = "6000000")
class InstructorControllerUnitTest : PostgresSQLContainerInitializer() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorServiceImpl

    @Test
    fun `addInstructor - should return created instructor`() {

        every { instructorServiceMock.addNewInstructor(any()) } returns instructorDTO(id = 1)

        val savedInstructorDto = webTestClient.post()
            .uri("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(instructorDTO())
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult()
            .responseBody

        assertNotNull(savedInstructorDto?.id)
    }

    @Test
    fun `addInstructor - validation failure`() {

        val instructorDto: InstructorDto = instructorDTO(name = "")

        every { instructorServiceMock.addNewInstructor(any()) } returns instructorDTO(id = 1)

        val response = webTestClient.post()
            .uri("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(instructorDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String()::class.java)
            .returnResult()
            .responseBody

        assertEquals(
            "[\"name is required\"]",
            response
        )
    }

    @Test
    fun `addInstructor - exception`() {

        val instructorDto: InstructorDto = instructorDTO()
        val errorMessage = "Unexpected Error Occurred!"
        every { instructorServiceMock.addNewInstructor(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient.post()
            .uri("/api/instructors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(instructorDto)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String()::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }


}