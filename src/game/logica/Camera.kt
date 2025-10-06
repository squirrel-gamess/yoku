package game

import korlibs.korge.view.*
import korlibs.math.*

object Camera {
    fun attach(
        worldContainer: Container,
        player: View,
        mapWidth: Double,
        mapHeight: Double,
        zoom: Double = 1.2
    ) {
        val screenWidth = worldContainer.stage?.views?.virtualWidth?.toDouble() ?: 0.0
        val screenHeight = worldContainer.stage?.views?.virtualHeight?.toDouble() ?: 0.0

        worldContainer.scale = zoom

        worldContainer.addUpdater {
            val targetX = -(player.x) * worldContainer.scale + screenWidth / 2
            val targetY = -(player.y - player.height / 2) * worldContainer.scale + screenHeight / 2




            val clampedX = targetX.coerceIn(-mapWidth * worldContainer.scale + screenWidth, 0.0)
            val clampedY = targetY.coerceIn(-mapHeight * worldContainer.scale + screenHeight, 0.0)

            // movimento suave
            worldContainer.x += (clampedX - worldContainer.x) * 0.1
            worldContainer.y += (clampedY - worldContainer.y) * 0.1

        }
    }
}
