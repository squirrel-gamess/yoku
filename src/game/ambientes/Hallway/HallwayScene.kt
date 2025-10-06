package game.ambientes.Hallway

import game.*

import korlibs.korge.scene.*
import korlibs.korge.view.*

object HallwayScene {
    suspend fun Render(container: Container): Container {
        val worldContainer = Container().addTo(container)
        val world = loadMap1()
        renderMap1(worldContainer, world)
        return worldContainer
    }
}
