package com.example.proyecto4

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
classes = arrayOf(proyecto4Application::class),
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Proyecto4ApplicationTests (@Autowired val mockMvc: MockMvc) {

@Autowired
    private lateinit var coderRepository: MovieRepository

    @AfterEach
    fun tearDown() {
        coderRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `returns the existing coders`() {
        addTestCoders()
        mockMvc.perform(MockMvcRequestBuilders.get("/coders"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[*]", Matchers.hasSize<Int>(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("Yeraldin")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].favouriteLanguage", Matchers.equalTo("Java")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo("Marta")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].favouriteLanguage", Matchers.equalTo("Kotlin")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.equalTo("Daniela")))
            .andExpect(MockMvcResultMatchers.jsonPath("$[2].favouriteLanguage", Matchers.equalTo("Python")))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @Throws(Exception::class)
    fun `allows to create a new coder`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Laura\", \"favouriteLanguage\": \"PHP\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk)
        val coders: List<Coder> = coderRepository.findAll()
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
    fun `allows to find a coder by id`() {
        val coder: Coder = coderRepository.save(Coder("Marta", "Kotlin"))
        mockMvc.perform(MockMvcRequestBuilders.get("/coders/" + coder.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Marta")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.favouriteLanguage", Matchers.equalTo("Kotlin")))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error if trying to get a coder that does not exist`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/coders/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    @Throws(Exception::class)
    fun `allows to delete a coder by id`() {
        val coder: Coder = coderRepository.save(Coder("Marta", "Kotlin"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/coders/" + coder.id))
            .andExpect(MockMvcResultMatchers.status().isOk)
        val coders: List<Coder> = coderRepository.findAll()
        MatcherAssert.assertThat(
            coders, Matchers.not(
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
    fun `returns an error if trying to delete a coder that does not exist`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/coders/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }


    @Test
    @Throws(Exception::class)
    fun `allows to modify a coder`() {
        val coder: Coder = coderRepository.save(Coder("Yeraldin", "Java"))
        mockMvc.perform(
            MockMvcRequestBuilders.put("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + coder.id + "\", \"name\": \"Yeraldin\", \"favouriteLanguage\": \"Ruby\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk)
        val coders: List<Coder> = coderRepository.findAll()
        MatcherAssert.assertThat(coders, Matchers.hasSize(1))
        MatcherAssert.assertThat(coders[0].name, Matchers.equalTo("Yeraldin"))
        MatcherAssert.assertThat(coders[0].favouriteLanguage, Matchers.equalTo("Ruby"))
    }

    @Test
    @Throws(Exception::class)
    fun `returns an error when trying to modify a coder that does not exist`() {
        addTestCoders()
        mockMvc.perform(
            MockMvcRequestBuilders.put("/coders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + -1 + "\", \"name\": \"Pepita\", \"favouriteLanguage\": \"C++\" }")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    private fun addTestCoders() {
        val coders: List<Coder> = listOf(
            Coder("Yeraldin", "Java"),
            Coder("Marta", "Kotlin"),
            Coder("Daniela", "Python")
        )
        coders.forEach(coderRepository::save)
    }


}


