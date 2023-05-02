package com.example.proyecto4

import com.example.proyecto4.repositories.Movie
import com.example.proyecto4.repositories.MovieRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(
classes = arrayOf(Proyecto4Application::class),
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Proyecto4AplicationTests(@Autowired val mockMvc: MockMvc) {

    @Autowired
    private lateinit var movieRepository: MovieRepository

    @AfterEach
    fun tearDown() {
        movieRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `returns the existing movies`() {
        addTestMovies()
        // peticion http get localhost:... /movies
        mockMvc.perform(get("/movies"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[*]", hasSize<Int>(16)))
            .andExpect(jsonPath("$[0].title", equalTo("Ratatouille")))
            .andExpect(jsonPath("$[0].coverImage", equalTo("https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg")))
            .andExpect(jsonPath("$[0].director", equalTo("Brad Bird")))
            .andExpect(jsonPath("$[0].releaseYear", equalTo(2007)))
            .andExpect(jsonPath("$[0].synopsis", equalTo("La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia.")))

            .andDo(print())
    }

    @Test
    @Throws(Exception::class)
    fun `allows to create a new movie`() {
        mockMvc.perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Frozen\", \"coverImage\": \"https://lumiere-a.akamaihd.net/v1/images/p_frozen_18373_3131259c.jpeg?region=0%2C0%2C540%2C810\", \"director\": \"Chris Buck\", \"releaseYear\":2013, \"synopsis\": \"Una profecía condena al reino de Arandelle a vivir en un invierno eterno. La joven Anna, el temerario montañero Kristoff y el reno Sven deben emprender un viaje épico y lleno de aventuras en busca de Elsa, la hermana de Anna y Reina de las Nieves. Ella es la única que puede poner fin al gélido hechizo.\" }")
        ).andExpect(status().isOk)
        val movies: List<Movie> = movieRepository.findAll()
        assertThat(
            movies, contains(
                allOf(
                    hasProperty("title", `is`("Ratatouille")),
                    hasProperty("coverImage", `is`("https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg")),
                    hasProperty("director", `is`("Brad Bird")),
                    hasProperty("releaseYear", `is`(2007)),
                    hasProperty("synopsis", `is`("La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."))

                )
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun `allows to find a movie by id`() {
        val movie: Movie = movieRepository.save(Movie("Ratatouille", "https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg","Brad Bird",2007, "La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."))
        mockMvc.perform(get("/movies/" + movie.id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title", equalTo("Ratatouille")))
            .andExpect(jsonPath("$.coverImage", equalTo("https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg")))
            .andExpect(jsonPath("$.director", equalTo("Brad Bird")))
            .andExpect(jsonPath("$.releaseYear", equalTo(2007)))
            .andExpect(jsonPath("$.synopsis", equalTo("La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia.")))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error if trying to get a movie that does not exist`() {
        mockMvc.perform(get("/movies/170"))
            .andExpect(status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun `allows to delete a movie by id`() {
        val movie: Movie = movieRepository.save(Movie("Ratatouille", "https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg","Brad Bird",2007,"La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."))
        mockMvc.perform(delete("/movies/" + movie.id))
            .andExpect(status().isOk)
        val movies: List<Movie> = movieRepository.findAll()
        assertThat(
            movies, not(
                contains(
                    allOf(
                        hasProperty("title", `is`("Ratatouille")),
                        hasProperty("coverImage", `is`("https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg")),
                        hasProperty("director", `is`("Brad Bird")),
                        hasProperty("releaseYear", `is`(2007)),
                        hasProperty("synopsis", `is`("La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."))
                    )
                )
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error if trying to delete a movie that does not exist`() {
        mockMvc.perform(delete("/movies/1"))
            .andExpect(status().isNotFound())
    }


    @Test
    @Throws(Exception::class)
    fun `allows to modify a movie`() {
        val movie: Movie = movieRepository.save(Movie(
            "Ratatouille",
            "https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg",
            "Brad Bird",
            2007,
            "La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."
        ))
        mockMvc.perform(
            put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \" + movieRepository.id + \", \"title\": \"Ratatouille\", \"coverImage\": \"https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg\", \"director\": \"Brad Bird\", \"releaseYear\": 2007, \"synopsis\": \"La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia.\" }")
        ).andExpect(status().isOk)
        val movies: List<Movie> = movieRepository.findAll()
        assertThat(movies, hasSize(1))
        assertThat(movies[0].title, equalTo("Ratatouille"))
        assertThat(movies[0].coverImage, equalTo("https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg"))
        assertThat(movies[0].director, equalTo("Brad Bird"))
        assertThat(movies[0].releaseYear, equalTo(2007))
        assertThat(movies[0].synopsis, equalTo("La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error when trying to modify a movie that does not exist`() {
        addTestMovies()
        mockMvc.perform(
            put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \" + -1 + \", \"movie\": \"Ratatouille\", \"coverImage\": \"https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg\", \"director\": \"Brad Bird\", \"releaseYear\": 2007, \"synopsis\": \"La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de uno de los cocineros más prestigiosos de Francia.\" }")
        ).andExpect(status().isNotFound())
    }

    private fun addTestMovies() {
        val movies: List<Movie> = listOf(
            Movie(
                "Ratatouille",
                "https://cgmoviereview.files.wordpress.com/2014/11/cover58.jpg",
                "Brad Bird",
                2007,
                "La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."
            ),
            Movie(
                "Jurassic Park",
                "https://pics.filmaffinity.com/Parque_Jur_sico_Jurassic_Park-187298880-large.jpg",
                "Steven Spielberg",
                1993,
                "El multimillonario John Hammond hace realidad su sueño de clonar dinosaurios del Jurásico y crear con ellos un parque temático en una isla. Antes de abrir el parque al público general, Hammond invita a una pareja de científicos y a un matemático para que comprueben la viabilidad del proyecto. Sin embargo, el sistema de seguridad falla y los dinosaurios se escapan."
            ),

            Movie(
                "Cruella",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcR5L6XSFSml9zrMqe_MPjzH-4pBZRHKl7ytjiEQvYXVZZxdQp1R",
                "Craig Gillespie",
                2021,
                "Decidida a convertirse en una exitosa diseñadora de moda, una joven y creativa estafadora llamada Estella se asocia con un par de ladrones para sobrevivir en las calles de Londres. Sin embargo, cuando su talento para la moda llama la atención de la legendaria diseñadora, la Baronesa von Hellman, Estella cambia el rumbo de su vida hasta que una serie de acontecimientos la llevan a asumir su lado malvado y a convertirse en la estridente y vengativa Cruella."
            ),
            Movie(
                "Mean Girls",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSp_2JWhvdh8b7CT6y62q0alRgO9u_M9JeKRbvPjF7yzBU9HbdE",
                "Mark Waters",
                2004,
                "Una joven adolescente, Cady, acostumbrada a vivir en África con sus padres zoólogos, se encuentra una nueva jungla cuando se muda a Illinois. Allí acude a la escuela pública, donde se enamorará del ex-novio de la chica más popular del colegio."
            ),
            Movie(
                "Lady Bird",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQV7rVedyzgHhUV2Gz3hnNyPQE5Dq9hvwU2gGUDiaZlnJaNdvwJ",
                "Greta Gerwig",
                2017,
                "Christine (Saoirse Ronan), que se hace llamar \"Lady Bird\", es una adolescente de Sacramento en su último año de instituto. La joven, con inclinaciones artísticas y que sueña con vivir en la costa Este, trata de ese modo encontrar su propio camino y definirse fuera de la sombra protectora de su madre (Laurie Metcalf)."
            ),
            Movie(
                "Suffragette",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQh611e6BEaNtP2pte3Y4tDO0Wc-y0DDAaSEpVG10B0cLFYsbP2",
                "Sarah Gavron",
                2015,
                "Inglaterra, principios del siglo XX. Años antes de que estalle la Primera Guerra Mundial, las mujeres exigen sus derechos políticos, más concretamente el derecho a votar. Las sufragistas inglesas están divididas entre las que defienden las protestas pacíficas, y las que luchan contra el gobierno sin piedad."
            ),
            Movie(
                "On the basis of sex",
                "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQARkveca3Xj616kV_Wb-3NhLXZU6OpSAqWsUTwPsik0pKRNJMQ",
                "Mimi Leder",
                2018,
                "Cuenta la historia de un histórico caso de discriminación que Ginsburg llevó adelante con su marido abogado, Martin, en 1972. El caso involucraba a Charles Moritz, un hombre soltero al que se le negó una deducción de impuestos de 296 dólares, pese a que cuidaba a su madre, por ser hombre."
            ),
            Movie(
                "La vie est belle",
                "data:image/jpeg;base64,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",
                "Roberto Benigni",
                1997,
                "Cuando estalla la Segunda Guerra Mundial, Guido, su esposa y su hijo, son internados en un campo de exterminio. Allí Guido hará manos y mangas para aliviar la terrible situación que están sufriendo, haciendo creer al niño que todo es sólo un juego."
            )

        )
        movies.forEach { movieRepository.save(it) }
    }


}


