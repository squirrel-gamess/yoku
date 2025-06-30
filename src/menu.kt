import game.ambientes.Quarto
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.io.async.*
import korlibs.korge.input.*
import korlibs.korge.ui.*
import korlibs.korge.view.align.*


class menu() : Scene() {
    override suspend fun SContainer.sceneMain() {
        SolidRect(512, 512, Colors["#2b2b2b"]).addTo(this)

        text("yoku", textSize = 64.0, color = Colors.WHITE) {
            centerOnStage()
            y -= 100.0
        }

        val btn = uiButton {
            // Configuração do tamanho
            size(220.0, 60.0)
            text = "JOGAR"
            textSize = 32.0
            textColor = Colors["#ffffff"]
            bgColorOut = Colors["#ff6f00"]  // cor de fundo normal
            radius = 1.0
            onClick {
                launchImmediately {
                    sceneContainer.changeTo { Quarto() }
                }
            }

            centerOn(this@sceneMain)
        }
    }
}