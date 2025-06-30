package game.ambientes

import game.*
import game.ambientes.Quarto.Hitbox
import game.debug.enableDebug
import game.logica.ColisaoManager
import game.logica.Door
import game.logica.KeyBinds
import game.personagens.Protagonist
import game.personagens.maeBase
import korlibs.image.color.Colors
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.scene.Scene
import korlibs.korge.view.*
import korlibs.math.geom.Point
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class Cozinha() : Scene() {
    // Adicionando hitboxes para a cozinha
    private val hitboxes = mutableListOf<Hitbox>()

    // Declarando as variáveis que serão usadas pelo diálogo
    private var balao: SolidRect? = null
    private var texto: Text? = null
    private var dialogoAtivo = false
    private var indiceDialogo = 0
    private var dialogosData: dialogos? = null
    private var actionKeyPressed = false // Controle para debounce da tecla F

    @Serializable
    data class Opcao(val resposta: String, val proximo_dialogo: String)

    @Serializable
    data class fala(
        val personagem: String,
        val fala: String,
        val opcoes: List<Opcao>? = null  // O "?" e "= null" deixam o campo opcional
    )

    @Serializable
    data class dialogos(val mae_intro: List<fala>)

    override suspend fun SContainer.sceneMain() {
        // Configura o container atual no ColisaoManager global
        ColisaoManager.setContainer(this)

        suspend fun carregarDialogos(): dialogos {
            val texto = resourcesVfs["dialogos/mãe.json"].readString()
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString<dialogos>(texto)
        }

        // Inicializa o KeyBinds para permitir o acesso ao input
        KeyBinds.initialize(views)

        val world = loadMap2()
        val protagonist = Protagonist(this)
        val mae = maeBase(this)

        renderMap2(this, world)
        mae.spawn(150.0, 60.0)
        protagonist.spawn(110.0, 350.0)

        // Definindo hitboxes para a cozinha
        hitboxes.addAll(listOf(
            Hitbox(0.0, 0.0, 16.0, 520.0),
            Hitbox(0.0, 0.0, 520.0, 128.0),
            Hitbox(500.0, 0.0, 16.0, 520.0),
            Hitbox(0.0, 500.0, 520.0, 16.0),
            Hitbox(190.0, 250.0, 96.0, 46.0), // mesa



        ))

        // Configurando as hitboxes no ColisaoManager global
        ColisaoManager.setHitboxes(hitboxes)
        protagonist.setHitboxes(hitboxes)

        // Ativando debug para visualização de hitboxes
        //enableDebug(protagonist, ColisaoManager)

        val sceneContainer = sceneContainer
        val porta = Door(sceneContainer, "corredor", Point(500, 250), protagonist)

        val evento = SolidRect(32, 32, Colors.RED)
        evento.position(150, 200)
        addChild(evento)
        // Register event interaction zone as circle for debug overlay
        val centerX = evento.x + evento.width / 2
        val centerY = evento.y + evento.height / 2
        val interactionRadius = 50.0
        ColisaoManager.addInteractableCircle(centerX, centerY, interactionRadius)

        // Carregar os diálogos uma única vez na inicialização
        launchImmediately {
            dialogosData = carregarDialogos()
        }

        // eventos especiais
        addUpdater {
            porta.update()

            val playerX = protagonist.playerPos.x
            val playerY = protagonist.playerPos.y
            // Usar o centro do retângulo como ponto de interação
            val eventoX = evento.x + evento.width / 2
            val eventoY = evento.y + evento.height / 2

            val raioColisao = 50.0 // Raio de colisão para o evento especial

            val distancia = kotlin.math.sqrt(
                (playerX - eventoX) * (playerX - eventoX) +
                        (playerY - eventoY) * (playerY - eventoY)
            )

            if (distancia <= raioColisao && dialogosData != null) {
                // Lógica de debounce: verifica se a tecla foi pressionada e depois solta
                if (KeyBinds.action()) {
                    if (!actionKeyPressed) {
                        actionKeyPressed = true

                        if (!dialogoAtivo) {
                            launchImmediately {
                                dialogoAtivo = true
                                indiceDialogo = 0
                                exibirDialogo(indiceDialogo, dialogosData!!)
                            }
                        } else {
                            launchImmediately {
                                indiceDialogo++

                                if (indiceDialogo < dialogosData!!.mae_intro.size) {
                                    exibirDialogo(indiceDialogo, dialogosData!!)
                                } else {
                                    balao?.let { removeChild(it) }
                                    texto?.let { removeChild(it) }
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

    private suspend fun SContainer.exibirDialogo(indice: Int, dialogos: dialogos) {
        // Removendo diálogos anteriores
        balao?.let { removeChild(it) }
        texto?.let { removeChild(it) }

        val falaAtual = dialogos.mae_intro[indice]

        // Criando novos elementos de diálogo
        balao = SolidRect(400, 64, Colors.WHITE).position(60, 40)
        texto = text("${falaAtual.personagem}: ${falaAtual.fala}", textSize = 12.0, color = Colors.BLACK) {
            position(80, 60)
        }

        addChild(balao!!)
        addChild(texto!!)
    }
}