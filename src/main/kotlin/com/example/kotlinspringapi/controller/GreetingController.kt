package com.example.kotlinspringapi.controller

import com.example.kotlinspringapi.service.GreetingService
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greeting")
class GreetingController(val greetingService: GreetingService) {

    companion object : KLogging()

    @Value("\${message}")
    lateinit var message: String

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String): String {
        logger.info { "Greeting $name" }
        return message + greetingService.retrieveGreeting(name)
    }
}