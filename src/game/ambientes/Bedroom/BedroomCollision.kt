
import game.logica.*

import game.logica.IHitbox
import korlibs.korge.scene.*
import korlibs.korge.view.*

object BedroomCollision: Scene() {
    // Definição da classe Hitbox implementando a interface IHitbox
    data class Hitbox(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double
    ) : IHitbox

    // Apenas uma lista de hitboxes
    val hitboxes = mutableListOf<Hitbox>()

    override suspend fun SContainer.sceneMain() {
        // Configura o container atual no ColisaoManager global
        ColisaoManager.setContainer(this)

        // Agora configuramos as hitboxes no ColisaoManager global
        val boxes = Hitboxes()
        ColisaoManager.setHitboxes(boxes)
    }


}

fun Hitboxes(): List<BedroomCollision.Hitbox> = listOf(
    BedroomCollision.Hitbox(0.0, 0.0, 32.0, 400.0),   // parede esquerda
    BedroomCollision.Hitbox(0.0, 0.0, 600.0, 32.0),   // parede superior
    BedroomCollision.Hitbox(568.0, 0.0, 32.0, 400.0), // parede direita
    BedroomCollision.Hitbox(0.0, 368.0, 600.0, 32.0), // parede inferior
    BedroomCollision.Hitbox(65.0, 240.0, 64.0, 32.0), // mesa pc
    BedroomCollision.Hitbox(130.0, 100.0, 64.0, 32.0),// cama
    BedroomCollision.Hitbox(220.0, 20.0, 64.0, 64.0)  // guarda-roupa
)


