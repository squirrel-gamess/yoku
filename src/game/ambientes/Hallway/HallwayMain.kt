package game.ambientes.Hallway

import game.logica.*
import korlibs.korge.scene.*
import korlibs.korge.view.*

class HallwayMain : Scene() {
    override suspend fun SContainer.sceneMain() {
        KeyBinds.initialize(views)

        val worldContainer = Container().addTo(this)
        val uiContainer = Container().addTo(this)

        // mapa
        HallwayScene.Render(worldContainer)

        // colisões
        ColisaoManager.setContainer(worldContainer)
        ColisaoManager.setHitboxes(HallwayHitboxes())

        // protagonista
        val protagonist = run(worldContainer, sceneContainer)

        // câmera
        HallwayView.setup(worldContainer, protagonist)

        // HUD
        uiContainer.text("HP: 100") { xy(20, 20) }
        
    }
}
