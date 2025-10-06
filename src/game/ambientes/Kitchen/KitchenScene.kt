package game.ambientes.Kitchen

import game.*
import korlibs.korge.scene.*
import korlibs.korge.view.*

object KitchenScene: Scene() {

    suspend fun Render(Container: Container){

        val world = loadMap2()
        renderMap2(Container, world)

    }

}
