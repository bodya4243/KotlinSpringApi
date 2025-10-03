package com.example.controller

import com.example.kotlinspringapi.KotlinSpringApiApplication
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import util.PostgresSQLContainerInitializer

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [KotlinSpringApiApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
class GreetingControllerIntgTest : PostgresSQLContainerInitializer() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveGreeting() {

        val name = "dilip"

        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("Hello from default profileHello, $name!", result.responseBody)
    }
}