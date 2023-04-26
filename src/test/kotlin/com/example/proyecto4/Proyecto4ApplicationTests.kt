package com.example.proyecto4

import com.example.proyecto4.repositories.Movie
import com.example.proyecto4.repositories.MovieRepository
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
class = arrayOf(proyecto4Application::class),
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Proyecto4ApplicationTests(@Autowired val mockMvc: MockMvc) {

    @Autowired
    private lateinit var movieRepository: MovieRepository

    @AfterEach
    fun tearDown() {
        movieRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `returns the existing movie`() {
        addTestMovies()
        mockMvc.perform(MockMvcRequestBuilders.get("/movies"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize<Int>(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.equalTo("Ratatouille")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].director", Matchers.equalTo("Java")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo("Marta")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].favouriteLanguage", Matchers.equalTo("Kotlin")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.equalTo("Daniela")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].favouriteLanguage", Matchers.equalTo("Python")))
            .andDo(MockMvcResultHandlers.print())
    }

    private fun addTestMovies() {
        TODO("Not yet implemented")
    }

    @Test
    @Throws(Exception::class)
    fun `allows to create a new movie`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Laura\", \"favouriteLanguage\": \"PHP\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk)
        val coders: List<Movie> = movieRepository.findAll()
        MatcherAssert.assertThat(
            coders, Matchers.contains(
                Matchers.allOf(
                    Matchers.hasProperty("name", Matchers.`is`("Laura")),
                    Matchers.hasProperty("favouriteLanguage", Matchers.`is`("PHP"))
                )
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun `allows to find a movie by id`() {
        val movie: Movie = movieRepository.save(Movie("", ""))
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + movie.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.favouriteLanguage", Matchers.equalTo("")))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error if trying to get a coder that does not exist`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/movies/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun `allows to delete a coder by id`() {
        val movie: Movie = movieRepository.save(Movie("Marta", "Kotlin"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/coders/" + coder.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
        val movies: List<Movie> = movieRepository.findAll()
        MatcherAssert.assertThat(
            movies, Matchers.not(
                Matchers.contains(
                    Matchers.allOf(
                        Matchers.hasProperty("name", Matchers.`is`("Marta")),
                        Matchers.hasProperty("favouriteLanguage", Matchers.`is`("Kotlin"))
                    )
                )
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error if trying to delete a movie that does not exist`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }


    @Test
    @Throws(Exception::class)
    fun `allows to modify a movie`() {
        val coder: Movie = movieRepository.save(Movie("Yeraldin", "Java"))
        mockMvc.perform(
            MockMvcRequestBuilders.put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + coder.id + "\", \"name\": \"Yeraldin\", \"favouriteLanguage\": \"Ruby\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk)
        val movie: List<Movie> = movieRepository.findAll()
        MatcherAssert.assertThat(Movie, Matchers.hasSize(1))
        MatcherAssert.assertThat(Movie[0].name, Matchers.equalTo("Yeraldin"))
        MatcherAssert.assertThat(Movie[0].favouriteLanguage, Matchers.equalTo("Ruby"))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error when trying to modify a movie that does not exist`() {
        addTestMovies()
        mockMvc.perform(
            MockMvcRequestBuilders.put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + -1 + "\", \"name\": \"Pepita\", \"favouriteLanguage\": \"C++\" }")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    private fun addTestMovie() {
        val movies: List<Movie> = listOf(
            Movie("", ""),
            Movie("", ""),
            Movie("", "")
        )
        movies.forEach { movieRepository.save(it) }
    }


}


