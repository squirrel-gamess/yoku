package game.ambientes

import game.*
import game.debug.enableDebug
import game.logica.ColisaoManager
import game.logica.Door
import game.logica.IHitbox
import game.logica.KeyBinds
import game.personagens.Protagonist
import korlibs.korge.scene.Scene
import korlibs.korge.view.*
import korlibs.math.geom.Point

class Quarto : Scene() {
    // Definição da classe Hitbox implementando a interface IHitbox
    data class Hitbox(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double
    ) : IHitbox

    // Apenas uma lista de hitboxes
    private val hitboxes = mutableListOf<Hitbox>()

    override suspend fun SContainer.sceneMain() {
        // Configura o container atual no ColisaoManager global
        ColisaoManager.setContainer(this)

        KeyBinds.initialize(views)

        // Carregar e renderizar o mapa 'casa'
        val world = loadMap3()
        renderMap3(this, world)

        // Paredes completas da sala (600x400) sem sobreposição
        hitboxes.addAll(listOf(
            Hitbox(0.0, 0.0, 32.0, 400.0),            // parede esquerda
            Hitbox(0.0, 0.0, 600.0, 32.0),           // parede superior
            Hitbox(568.0, 0.0, 32.0, 400.0),      // parede direita
            Hitbox(0.0, 368.0, 600.0, 32.0),     // parede inferior
            Hitbox(65.0, 240.0, 64.0, 32.0),    // mesa pc
            Hitbox(130.0, 100.0, 64.0, 32.0),  // cama
            Hitbox(220.0, 20.0, 64.0, 64.0) // guarda-roupa

        ))

        // Agora configuramos as hitboxes no ColisaoManager global
        ColisaoManager.setHitboxes(hitboxes)

        // Criamos um protagonista diretamente
        val protagonist = Protagonist(this)
        protagonist.spawn()

        protagonist.playerPos = Point(350.0, 100.0)
        protagonist.setHitboxes(hitboxes)

        // Ativa debug de hitboxes
       // enableDebug(protagonist, ColisaoManager)

        val sceneContainer = sceneContainer
        val porta = Door(sceneContainer, "corredor", Point(350.0, 80.0), protagonist)

        addUpdater {
            porta.update()
        }


        val posicao = protagonist.playerPos
        println("Posição do personagem: x=${posicao.x}, y=${posicao.y}")
    }
}