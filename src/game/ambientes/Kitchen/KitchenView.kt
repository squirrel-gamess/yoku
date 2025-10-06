package game.ambientes.Kitchen

import game.Camera
import game.personagens.Protagonist
import korlibs.korge.view.*

object KitchenView    {
    fun setup(worldContainer: Container, protagonist: Protagonist) {
        Camera.attach(worldContainer, protagonist.image, mapWidth = 800.0, mapHeight = 600.0, zoom = 2.0)

    }
}
