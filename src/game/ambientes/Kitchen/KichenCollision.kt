package game.ambientes.Kitchen


import game.logica.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import game.debug.enableDebug
import game.personagens.Protagonist

object KitchenCollision : Scene() { // renomeado

    // Definição da classe Hitbox específica da cozinha
    data class Hitbox(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double
    ) : IHitbox

    private val hitboxes = mutableListOf<Hitbox>()

    // Referência opcional ao protagonista para o overlay de debug
    private var protagonist: Protagonist? = null
    private var debugEnabled: Boolean = false

    fun registerProtagonist(p: Protagonist) { protagonist = p }
    fun enableDebugOverlay() { debugEnabled = true }

    // Novo helper direto para ativar debug imediatamente (sem depender de sceneMain)
    fun attachDebug(container: Container, protagonist: Protagonist) {
        ColisaoManager.setContainer(container)
        ColisaoManager.setHitboxes(KitchenHitboxes())
        container.enableDebug(protagonist, ColisaoManager)
    }

    override suspend fun SContainer.sceneMain() {
        // Configura o container atual no ColisaoManager global
        ColisaoManager.setContainer(this)

        // Prepara e registra as hitboxes da cozinha
        hitboxes.clear()
        hitboxes.addAll(KitchenHitboxes())
        ColisaoManager.setHitboxes(hitboxes)

        // Ativa overlay de debug se configurado externamente
        if (debugEnabled) {
            protagonist?.let { this.enableDebug(it, ColisaoManager) }
        }
    }
}

fun KitchenHitboxes(): List<KitchenCollision.Hitbox> = listOf(
    // Definindo hitboxes para a cozinha
    KitchenCollision.Hitbox(0.0, 0.0, 16.0, 520.0),
    KitchenCollision.Hitbox(0.0, 0.0, 520.0, 128.0),
    KitchenCollision.Hitbox(500.0, 0.0, 16.0, 520.0),
    KitchenCollision.Hitbox(0.0, 500.0, 520.0, 16.0),
    KitchenCollision.Hitbox(190.0, 250.0, 96.0, 46.0) // mesa
)
