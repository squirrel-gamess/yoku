package game.ambientes.Bedroom

import BedroomScene
import BedroomScript
import Hitboxes
import game.logica.*
import game.personagens.*
import korlibs.korge.scene.*
import korlibs.korge.view.*

class BedroomMain : Scene() {
    override suspend fun SContainer.sceneMain() {
        KeyBinds.initialize(views)

        // cria containers de layers
        val worldContainer = Container().addTo(this)
        val uiContainer = Container().addTo(this)

        // renderiza cenário só uma vez, dentro do mundo
        BedroomScene.Render(worldContainer)

        // colisões
        ColisaoManager.setContainer(worldContainer)
        ColisaoManager.setHitboxes(Hitboxes())

        // scripts → já retorna o protagonista
        val protagonist = BedroomScript.run(worldContainer, sceneContainer)

        // câmera segue o mesmo protagonista
        BedroomView.setup(worldContainer, protagonist.image)

        // HUD exemplo
        uiContainer.text("HP: 100") { xy(20, 20) }
    }
}


