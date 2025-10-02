package com.example.kotlinspringapi.service

import org.springframework.stereotype.Service

@Service
class GreetingServiceImpl : GreetingService {
    override fun retrieveGreeting(name: String): String {
        return "Hello, $name!"
    }
}