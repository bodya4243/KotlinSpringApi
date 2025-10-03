package com.example.controller

import com.example.kotlinspringapi.KotlinSpringApiApplication
import com.example.kotlinspringapi.dto.InstructorDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import util.PostgresSQLContainerInitializer
import util.instructorDTO


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinSpringApiApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
class InstructorControllerIntgTest : PostgresSQLContainerInitializer() {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun addInstructor_ok() {
        val name = "bodya4243"

        val instructorDto: InstructorDto = instructorDTO()

        val result = webTestClient
            .post()
            .uri("api/instructors", name)
            .bodyValue(instructorDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDto::class.java)
            .returnResult()

        Assertions.assertEquals(1, result.responseBody?.id)
    }
}