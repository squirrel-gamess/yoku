import game.logica.*



import game.personagens.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

object BedroomScript: Scene() {

    override suspend fun SContainer.sceneMain() {

        // Criamos um protagonista diretamente
        val protagonist = Protagonist(this)
        protagonist.spawn()

        protagonist.playerPos = Point(350.0, 100.0)
        protagonist.setHitboxes(Hitboxes())

        val sceneContainer = sceneContainer
        val porta = Door(sceneContainer, "corredor", Point(350.0, 80.0), protagonist)

        addUpdater {
            porta.update()
        }
        
    }

    // Helper público para executar a lógica sem trocar de cena
    suspend fun run(container: Container, sceneContainer: SceneContainer) = with(container) {
        val protagonist = Protagonist(this)
        protagonist.spawn()

        protagonist.playerPos = Point(350.0, 100.0)
        protagonist.setHitboxes(Hitboxes())

        val porta = Door(sceneContainer, "corredor", Point(350.0, 80.0), protagonist)
        addUpdater { porta.update() }

        protagonist
    }
}
