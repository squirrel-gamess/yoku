package game.ambientes.Kitchen

import kotlinx.serialization.Serializable

@Serializable
data class Fala(
    val personagem: String,
    val fala: String
)

@Serializable
data class dialogos(
    val mae_intro: List<Fala>
)

