package game.ambientes.Hallway



import game.*
import game.Camera
import game.personagens.*
import korlibs.korge.view.*

object HallwayView {
    fun setup(worldContainer: Container, protagonist: Protagonist) {
        Camera.attach(worldContainer, protagonist.image, mapWidth = 800.0, mapHeight = 600.0, zoom = 2.0)
    }
}

