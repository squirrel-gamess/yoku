package game

import korlibs.korge.view.SContainer
import korlibs.korge.scene.SceneContainer
import korlibs.math.geom.Point
import korlibs.io.async.launchImmediately

class Door(
    private val sceneContainer: SceneContainer,
    private val targetSceneName: String,
    private val position: Point,
    private val protagonist: Protagonist
) {
    private val interactionDistance = 100.0

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
<<<<<<< Updated upstream
=======
                    "cozinha" -> sceneContainer.changeTo { Cozinha() }
>>>>>>> Stashed changes
                    else -> sceneContainer.changeTo { Quarto() }
                }
            }
        }
    }
}