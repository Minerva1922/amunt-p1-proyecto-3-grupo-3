package com.example.proyecto4.repositories

import jakarta.persistence.*

//@Table(name = "Movie")
@Entity
data class Movie(
    var title: String,
    @Lob
    var coverImage: String,
    var director: String,
    var releaseYear: Int,
    var synopsis: String,



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
)