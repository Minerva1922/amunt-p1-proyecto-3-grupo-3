package com.example.proyecto4.controllers

import com.example.proyecto4.repositories.Movie
import com.example.proyecto4.repositories.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class MovieController(@Autowired private val movieRepository: MovieRepository) {

    @GetMapping("/movies")
    fun allMovies(): List<Movie?>? {
        return movieRepository.findAll()
    }

    @GetMapping("/movies/{id}")
    fun findMovie(@PathVariable id: Long): Movie? {
        return movieRepository.findById(id).orElseThrow { MovieNotFoundException() }
    }

    @PostMapping("/movies")
    fun addMovie(@RequestBody movie: Movie): Movie? {
        return movieRepository.save(movie)
    }
//
    @DeleteMapping("/movies/{id}")
    fun deleteMovieById(@PathVariable id: Long): Movie? {
        val movie: Movie = movieRepository.findById(id).orElseThrow { MovieNotFoundException() }
        movieRepository.deleteById(id)
        return movie
    }
//
    @PutMapping("/movies")
    fun updateMovieById(@RequestBody movie: Movie): Movie? {
        movie.id?.let { movieRepository.findById(Long).orElseThrow { MovieNotFoundException() } }
        return movieRepository.save(movie)
    }
}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "movie not found")
class MovieNotFoundException : RuntimeException()