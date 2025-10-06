
import game.ambientes.Bedroom.*
import game.ambientes.Kitchen.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.io.async.*
import korlibs.korge.input.*
import korlibs.korge.ui.*
import korlibs.korge.view.align.*
import korlibs.audio.sound.*
import korlibs.io.file.std.*


class menu() : Scene() {
    // faz uma referencia a musica para não deixar ela tocar em looping
    private var bgmChannel: SoundChannel? = null

    override suspend fun SContainer.sceneMain() {
        SolidRect(512, 512, Colors["#2b2b2b"]).addTo(this)

        text("yoku", textSize = 64.0, color = Colors.WHITE) {
            centerOnStage()
            y -= 100.0
        }

        uiButton {
            // Configuração do tamanho
            size(220.0, 60.0)
            text = "JOGAR"
            textSize = 32.0
            textColor = Colors["#ffffff"]
            bgColorOut = Colors["#ff6f00"]  // cor de fundo normal
            radius = 1.0
            onClick {
                // Ao iniciar o jogo, pare a música do menu
                bgmChannel?.stop()
                bgmChannel = null
                launchImmediately {
                    sceneContainer.changeTo { KitchenMain() }
                }
            }

            centerOn(this@sceneMain)
        }

        // Iniciar a música de fundo após a primeira interação do usuário (corrige autoplay no navegador)
        var started = false
        onClick {
            if (!started && (bgmChannel == null || !bgmChannel!!.playing)) {
                started = true
                launchImmediately { startMenuMusic() }
            }
        }
    }

    // Tocar música de fundo do menu (loop)
    private suspend fun startMenuMusic() {
        if (bgmChannel != null && bgmChannel!!.playing) return
        val bgm = resourcesVfs["/music/menu_theme.wav"].readMusic()
        bgmChannel = bgm.playForever()
        bgmChannel?.volume = 0.5
    }
}
