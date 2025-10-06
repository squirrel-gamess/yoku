package game.ambientes.Kitchen

import game.logica.*
import korlibs.korge.scene.*
import korlibs.korge.view.*

class KitchenMain : Scene() {
    override suspend fun SContainer.sceneMain() {
        // 1. Inicializa controles
        KeyBinds.initialize(views)

        // 2. Renderiza cenário da cozinha
        val worldCointainer = Container().addTo(this)
        val UIContainer = Container().addTo(this)
        KitchenScene.Render(worldCointainer)

        // 3. Configura colisões da cozinha
        ColisaoManager.setContainer(worldCointainer)
        ColisaoManager.setHitboxes(KitchenHitboxes())

        //4. Inicializa o protagonista
        val protagonist = KitchenScript.run(worldCointainer, sceneContainer, UIContainer)
        KitchenView.setup(worldCointainer, protagonist)

        //5. HUD
        UIContainer.text("HP: 100") { xy(20, 20) }
    }
}
