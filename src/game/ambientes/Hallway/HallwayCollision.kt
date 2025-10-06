package game.ambientes.Hallway

import game.logica.*
import korlibs.korge.scene.*
import game.logica.ColisaoManager
import korlibs.korge.view.*


object HallwayCollision: Scene() {

    // Definição da classe Hitbox específica do Hallway
    data class Hitbox(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double
    ) : IHitbox

    private val hitboxes = mutableListOf<Hitbox>()


    override suspend fun SContainer.sceneMain() {


        // Configura o container atual no    ColisaoManager global
        ColisaoManager.setContainer(this)

        // Prepara e registra as hitboxes do corredor
        hitboxes.clear()
        hitboxes.addAll(HallwayHitboxes())
        ColisaoManager.setHitboxes(hitboxes)
    }
}

// Factory público para as hitboxes do corredor
fun HallwayHitboxes(): List<HallwayCollision.Hitbox> = listOf(
    HallwayCollision.Hitbox(60.0, 0.0, 220.0, 100.0),
    HallwayCollision.Hitbox(0.0, 120.0, 110.0, 100.0),
    HallwayCollision.Hitbox(250.0, 100.0, 64.0, 500.0),
    HallwayCollision.Hitbox(0.0, 393.0, 110.0, 100.0),
)
