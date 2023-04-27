package com.example.proyecto4.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface MovieRepository: JpaRepository<Movie, Long> {
}