package game

import korlibs.io.async.launchImmediately
import korlibs.korge.scene.Scene
import korlibs.korge.view.SContainer
import korlibs.korge.view.addUpdater
import korlibs.math.geom.Point

class Quarto : Scene() {
    override suspend fun SContainer.sceneMain() {
        // Inicializa o KeyBinds para permitir o acesso ao input
        KeyBinds.initialize(views)

        val world = loadMap()
        val protagonist = Protagonist(this)
        renderMap(this, world)
        protagonist.spawn()
        protagonist.playerPos = Point(350, 0)

        // Use sceneContainer em vez de this para obter a referência correta
        val sceneContainer = sceneContainer
        val porta = Door(sceneContainer, "corredor", Point(350, 20), protagonist)

        // Adiciona o updater com launchImmediately para permitir chamar funções suspend
        addUpdater {
            launchImmediately {
                porta.update()
            }
        }

        val posicao = protagonist.playerPos
        println("Posição do personagem: x=${posicao.x}, y=${posicao.y}")
    }
}