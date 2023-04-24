package com.example.proyecto4.repositories

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DataLoader(private val movieRepository: MovieRepository) {
    @PostConstruct
    fun load() {
        val movies = listOf(
            Movie("Ratatouille","url de imagen", "Brad Bird", 2007,"La película narra la historia de una rata que sueña con convertirse en chef y para realizar su objetivo, decide hacer una alianza con el hijo de uno de los cocineros más prestigiosos de Francia."),
            Movie("Jurassic Park","url de imagen", "Steven Spielberg", 1993,"El multimillonario John Hammond hace realidad su sueño de clonar dinosaurios del Jurásico y crear con ellos un parque temático en una isla. Antes de abrir el parque al público general, Hammond invita a una pareja de científicos y a un matemático para que comprueben la viabilidad del proyecto. Sin embargo, el sistema de seguridad falla y los dinosaurios se escapan."),
            Movie("Cruella","url de imagen", "Craig Gillespie", 2021,"Decidida a convertirse en una exitosa diseñadora de moda, una joven y creativa estafadora llamada Estella se asocia con un par de ladrones para sobrevivir en las calles de Londres. Sin embargo, cuando su talento para la moda llama la atención de la legendaria diseñadora, la Baronesa von Hellman, Estella cambia el rumbo de su vida hasta que una serie de acontecimientos la llevan a asumir su lado malvado y a convertirse en la estridente y vengativa Cruella."),
            Movie("Mean Girls","url de imagen", "Mark Waters", 2004,"Una joven adolescente, Cady, acostumbrada a vivir en África con sus padres zoólogos, se encuentra una nueva jungla cuando se muda a Illinois. Allí acude a la escuela pública, donde se enamorará del ex-novio de la chica más popular del colegio."),
            Movie("Lady Bird","url de imagen", "Greta Gerwig", 2017,"Christine (Saoirse Ronan), que se hace llamar \"Lady Bird\", es una adolescente de Sacramento en su último año de instituto. La joven, con inclinaciones artísticas y que sueña con vivir en la costa Este, trata de ese modo encontrar su propio camino y definirse fuera de la sombra protectora de su madre (Laurie Metcalf)."),
            Movie("Suffragette","url de imagen", "Sarah Gavron", 2015,"Inglaterra, principios del siglo XX. Años antes de que estalle la Primera Guerra Mundial, las mujeres exigen sus derechos políticos, más concretamente el derecho a votar. Las sufragistas inglesas están divididas entre las que defienden las protestas pacíficas, y las que luchan contra el gobierno sin piedad."),
            Movie("On the basis of sex","url de imagen", "Mimi Leder", 2018,"Cuenta la historia de un histórico caso de discriminación que Ginsburg llevó adelante con su marido abogado, Martin, en 1972. El caso involucraba a Charles Moritz, un hombre soltero al que se le negó una deducción de impuestos de 296 dólares, pese a que cuidaba a su madre, por ser hombre."),
            Movie("La vie est belle","url de imagen", "Roberto Benigni", 1999,"Cuando estalla la Segunda Guerra Mundial, Guido, su esposa y su hijo, son internados en un campo de exterminio. Allí Guido hará manos y mangas para aliviar la terrible situación que están sufriendo, haciendo creer al niño que todo es sólo un juego.")

        )
        movieRepository.saveAll(movies)
        println("Cargamos datos de prueba cuando arrancamos el servidor: $movies")
    }
}