package game.ambientes.Kitchen

import Hitboxes
import game.logica.*
import game.personagens.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

object KitchenScript : Scene() {

    private const val DEBUG_KITCHEN = false // mude para true para ver hitboxes na cozinha

    override suspend fun SContainer.sceneMain() {

        val worldContainer = container()
        val uiContainer = container()

        val protagonist = Protagonist(worldContainer)
        val mae = maeBase(worldContainer)

        mae.spawn(150.0, 60.0)
        protagonist.spawn(110.0, 350.0)
        protagonist.setHitboxes(Hitboxes())

        if (DEBUG_KITCHEN) {
            KitchenCollision.attachDebug(worldContainer, protagonist)
        }

        // Carrega e anexa diálogo
        KitchenDialogue.load()
        KitchenDialogue.attach(worldContainer, protagonist, uiContainer)

        val stageContainer = sceneContainer
        val porta = Door(stageContainer, "corredor", Point(200, 450), protagonist)

        worldContainer.addUpdater {
            porta.update()
        }
    }

    // Helper público para executar a lógica sem trocar de cena
    suspend fun run(container: Container, sceneContainer: SceneContainer, uiContainer: Container) = with(container) {
        val protagonist = Protagonist(this)
        val mae = maeBase(this) // <-- adicionar mãe também aqui

        // Spawn dos personagens
        mae.spawn(150.0, 60.0)
        protagonist.spawn()
        protagonist.playerPos = Point(110.0, 350.0)
        protagonist.setHitboxes(Hitboxes())

        if (DEBUG_KITCHEN) {
            KitchenCollision.attachDebug(this, protagonist)
        }

        // Carrega e anexa diálogo
        KitchenDialogue.load()
        KitchenDialogue.attach(this, protagonist, uiContainer)

        // Porta (coordenadas unificadas com sceneMain)
        val porta = Door(sceneContainer, "corredor", Point(470.0, 250.0), protagonist)
        addUpdater { porta.update() }

        protagonist // 👈 devolve o protagonista
    }
}
