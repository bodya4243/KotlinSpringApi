package com.example.kotlinspringapi.service

interface GreetingService {
    fun retrieveGreeting(name: String): String
}