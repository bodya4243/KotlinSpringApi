package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
class GreetingControllerIntegrationTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @MockkBean
    lateinit var greetingService: GreetingService

    @Test
    fun `should return greeting with mocked service using MockK`() {
        val name = "Bohdan"
        val mockedServiceResponse = "Welcome!"

        every { greetingService.retrieveGreeting(name) } returns mockedServiceResponse

        val response = restTemplate.getForObject("/v1/greeting/$name", String::class.java)

        assertEquals("Hello, $mockedServiceResponse", response)
    }
}
