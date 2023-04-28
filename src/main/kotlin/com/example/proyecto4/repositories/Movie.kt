package com.example.proyecto4.repositories

import jakarta.persistence.*

@Table(name = "movies")
@Entity
data class Movie(
    var title: String,
    @Lob
    var coverImage: String,
    var director: String,
    var releaseYear: Int,
    @Lob
    var synopsis: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: String? = null,
)