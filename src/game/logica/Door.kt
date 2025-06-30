package game.logica

import game.personagens.Protagonist
import game.ambientes.Corredor
import game.ambientes.Cozinha
import game.ambientes.Quarto
import korlibs.korge.scene.SceneContainer
import korlibs.math.geom.Point
import korlibs.math.geom.Rectangle
import game.logica.ColisaoManager
import korlibs.io.async.launchImmediately

class Door(
    private val sceneContainer: SceneContainer,
    private val targetSceneName: String,
    private val position: Point,
    private val protagonist: Protagonist
) {
    private val interactionDistance = 50.0

    init {
        // Register this door's interaction zone as a circle for debug overlay
        ColisaoManager.addInteractableCircle(position.x, position.y, interactionDistance)
    }

    // Removido o suspend daqui
    fun update() {
        val dx = protagonist.playerPos.x - position.x
        val dy = protagonist.playerPos.y - position.y
        val distance = kotlin.math.sqrt(dx * dx + dy * dy)

        if (distance <= interactionDistance && KeyBinds.action()) {
            // Usando launchImmediately para chamar funções suspending
            sceneContainer.launchImmediately {
                when (targetSceneName) {
                    "quarto" -> sceneContainer.changeTo { Quarto() }
                    "corredor" -> sceneContainer.changeTo { Corredor() }
                    "cozinha" -> sceneContainer.changeTo { Cozinha() }
                    else -> sceneContainer.changeTo { Quarto() }
                }
            }
        }
    }
}