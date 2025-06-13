package game

import korlibs.korge.scene.Scene
import korlibs.korge.view.SContainer
import korlibs.korge.view.addUpdater
import korlibs.math.geom.Point

<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
// Adicione um construtor primário para a classe Corredor
class Corredor() : Scene() {
	override suspend fun SContainer.sceneMain() {
		// Inicializa o KeyBinds para permitir o acesso ao input
		KeyBinds.initialize(views)

		val world = loadMap1()
		val protagonist = Protagonist(this)
		renderMap1(this, world)
		protagonist.spawn(110.0, 350.0) // Define a posição inicial do protagonista
		// Use sceneContainer em vez de this para obter a referência correta
		val sceneContainer = sceneContainer
		val porta = Door(sceneContainer, "quarto", Point(150, 300), protagonist)
<<<<<<< Updated upstream

		addUpdater {
			porta.update()
=======
		val porta2 = Door(sceneContainer, "cozinha", Point(10, 250), protagonist)

		addUpdater {
			porta.update()
			porta2.update()
>>>>>>> Stashed changes
		}

		val posicao = protagonist.playerPos
		println("Posição do personagem: x=${posicao.x}, y=${posicao.y}")
	}
}