package com.example.kotlinspringapi.repository

import com.example.kotlinspringapi.model.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {

    fun findInstructorByName(name : String) : Instructor
}