package com.example.proyecto4.repositories

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DataLoader(private val movieRepository: MovieRepository) {
    @PostConstruct
    fun load() {
        val movies = listOf(
            Movie("Ratatouille","url de imagen", "Brad Bird", 2007,"lalalal"),
            Movie("Jurassic Park","url de imagen", "Steven Spielberg", 1993,"lalalal"),
            Movie("Cruella","url de imagen", "Craig Gillespie", 2021,"lalalal"),
            Movie("Mean Girls","url de imagen", "Mark Waters", 2004,"lalalal"),
            Movie("Lady Bird","url de imagen", "Greta Gerwig", 2017,"lalalal"),
            Movie("Suffragette","url de imagen", "Sarah Gavron", 2015,"lalalal"),
            Movie("On the basis of sex","url de imagen", "Mimi Leder", 2018,"lalalal"),
            Movie("La vie est belle","url de imagen", "Roberto Benigni", 1999,"lalalal")

        )
        movieRepository.saveAll(movies)
        println("Cargamos datos de prueba cuando arrancamos el servidor: $movies")
    }
}