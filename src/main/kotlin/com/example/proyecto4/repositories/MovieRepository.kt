package com.example.proyecto4.repositories

import jakarta.persistence.metamodel.SingularAttribute
import org.springframework.data.jpa.domain.AbstractPersistable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import java.util.*


@Repository
interface MovieRepository: JpaRepository<Movie, Long> {
    fun findById(id: SingularAttribute<AbstractPersistable<Serializable>, Serializable>?): Optional<Movie>
    //abstract fun findById(it: String): Any
}