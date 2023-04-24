package com.example.proyecto4.repositories

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DataLoader(private val movieRepository: MovieRepository) {
    @PostConstruct
    fun load() {
        val movies = listOf(
            Movie("p","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal"),
            Movie("peli1","url de imagen", "spielberg", 1994,"lalalal")

        )
        movieRepository.saveAll(movies)
        println("Cargamos datos de prueba cuando arrancamos el servidor: $movies")
    }
}