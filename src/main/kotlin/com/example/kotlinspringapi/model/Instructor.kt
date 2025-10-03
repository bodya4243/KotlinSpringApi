package com.example.kotlinspringapi.model

import jakarta.persistence.*

@Table(name = "instructors")
@Entity
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var name: String = "",

    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var courses: MutableList<Course> = mutableListOf()
) {
    override fun toString(): String {
        return "Instructor(id=$id, name=$name)"
    }
}
