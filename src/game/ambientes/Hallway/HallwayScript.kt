package game.ambientes.Hallway

import game.logica.*
import game.personagens.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*

suspend fun run(container: Container, sceneContainer: SceneContainer): Protagonist = with(container) {
    val protagonist = Protagonist(this)
    protagonist.spawn(150.0, 300.0)
    protagonist.setHitboxes(HallwayHitboxes())

    val porta = Door(sceneContainer, "quarto", Point(150, 500), protagonist)
    val porta2 = Door(sceneContainer, "cozinha", Point(10, 350), protagonist)

    addUpdater {
        porta.update()
        porta2.update()
    }

    protagonist // 👈 devolve o protagonista
}
