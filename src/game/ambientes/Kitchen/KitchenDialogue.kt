package game.ambientes.Kitchen

import game.logica.ColisaoManager
import game.logica.KeyBinds
import game.personagens.Protagonist
import korlibs.image.color.Colors
import korlibs.image.font.*        // 👈 importa Font, DefaultTtfFont, readTtfFont
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*
import korlibs.time.seconds
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object KitchenDialogue {

    @Serializable
    data class Opcao(val resposta: String, val proximo_dialogo: String)

    @Serializable
    data class Fala(
        val personagem: String,
        val fala: String,
        val opcoes: List<Opcao>? = null,
        val sprite: String? = null

    )

    @Serializable
    data class Dialogos(val mae_intro: List<Fala>)

    private var texto: Text? = null
    private var dialogoAtivo = false
    private var indiceDialogo = 0
    private var dialogosData: Dialogos? = null
    private var actionKeyPressed = false
    private var dialogoUI: DialogoAnimacao? = null

    // 👇 guarda a fonte numa propriedade (acessível em todo o objeto)
    private var fonteDialogo: Font? = null

    /** Carrega o JSON e a fonte (chame no início da Scene) */
    suspend fun load() {
        val raw = resourcesVfs["dialogos/mãe.json"].readString()
        dialogosData = Json { ignoreUnknownKeys = true }.decodeFromString(raw)

        // carrega a fonte do resources (ex.: src/commonMain/resources/fonts/ari_w.ttf)
        fonteDialogo = resourcesVfs["fonts/ari-w.ttf"].readTtfFont()
    }

    fun attach(worldContainer: Container, protagonist: Protagonist, uiContainer: Container) {
        val evento = SolidRect(32, 32, Colors.RED).position(150, 200)
        worldContainer.addChild(evento)

        val eventoX = evento.x + evento.width / 2
        val eventoY = evento.y + evento.height / 2
        val raioColisao = 50.0
        ColisaoManager.addInteractableCircle(eventoX, eventoY, raioColisao)

        worldContainer.addUpdater {
            val dados = dialogosData ?: return@addUpdater
            val playerX = protagonist.playerPos.x
            val playerY = protagonist.playerPos.y

            val distancia = kotlin.math.sqrt(
                (playerX - eventoX) * (playerX - eventoX) +
                    (playerY - eventoY) * (playerY - eventoY)
            )

            if (distancia <= raioColisao) {
                if (KeyBinds.action()) {
                    if (!actionKeyPressed) {
                        actionKeyPressed = true
                        worldContainer.stage?.views?.launchImmediately {
                            if (!dialogoAtivo) {
                                dialogoAtivo = true
                                indiceDialogo = 0
                                exibirDialogo(uiContainer, indiceDialogo, dados)
                            } else {
                                indiceDialogo++
                                if (indiceDialogo < dados.mae_intro.size) {
                                    exibirDialogo(uiContainer, indiceDialogo, dados)
                                } else {
                                    limparDialogo(uiContainer)
                                    dialogoAtivo = false
                                }
                            }
                        }
                    }
                } else {
                    actionKeyPressed = false
                }
            }
        }
    }

    private fun limparDialogo(uiContainer: Container) {
        texto?.removeFromParent()
        texto = null
        dialogoUI?.remove()
        dialogoUI = null
    }

    private suspend fun exibirDialogo(uiContainer: Container, indice: Int, dialogos: Dialogos) {
        val falaAtual = dialogos.mae_intro[indice]

        if (dialogoUI == null) {
            dialogoUI = DialogoAnimacao()
            dialogoUI?.spawnDialogo(uiContainer)
        }
        val duracaoAnim = 0.3.seconds

        falaAtual.sprite?.let { spriteFile ->
            dialogoUI?.trocarImagem(spriteFile, duracaoAnim)
        } ?: dialogoUI?.animarFala(duracaoAnim)

        texto?.removeFromParent()

        val fontToUse = fonteDialogo ?: DefaultTtfFont
        texto = uiContainer.text(
            text = "${falaAtual.personagem}: ${falaAtual.fala}",
            textSize = 32.0,
            color = Colors.BLACK,
            font = fontToUse
        ) {
            position(20.0, 400)
            smoothing = false
        }

        // garante que o texto fique acima do bg/animação
        texto?.bringToTop()
        // (opcional) texto?.zIndex = 1000.0/
    }
}
