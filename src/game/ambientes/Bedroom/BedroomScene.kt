import game.*

import korlibs.korge.scene.*
import korlibs.korge.view.*

object BedroomScene: Scene() {

    suspend fun Render (Container: Container): Container {
        val worldContainer = Container().addTo(Container)
        val world = loadMap3()
        renderMap3(Container, world)
    return worldContainer
    }
}
