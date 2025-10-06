package game.ambientes.Bedroom

import game.*
import game.Camera
import korlibs.korge.view.*

object BedroomView {
    fun setup(container: Container, player: View) {
        val worldContainer = container

        // valores fixos por enquanto (podes extrair do LDtk depois)
        val mapWidth = 600.0
        val mapHeight = 400.0

        Camera.attach(worldContainer, player, mapWidth, mapHeight, zoom = 2.0)
    }
}

