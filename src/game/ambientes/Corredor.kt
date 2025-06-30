package game.ambientes

import game.*
import game.ambientes.Quarto.Hitbox
import game.debug.enableDebug
import game.logica.ColisaoManager
import game.logica.Door
import game.logica.KeyBinds
import game.personagens.Protagonist
import korlibs.korge.scene.Scene
import korlibs.korge.view.SContainer
import korlibs.korge.view.addUpdater
import korlibs.math.geom.Point


class Corredor() : Scene() {
	private val hitboxes = mutableListOf<Hitbox>()

	override suspend fun SContainer.sceneMain() {
		// Define este container como o atual no ColisaoManager global
		ColisaoManager.setContainer(this)

		// Inicializa o KeyBinds para permitir o acesso ao input
		KeyBinds.initialize(views)

		val world = loadMap1()
		val protagonist = Protagonist(this)

		renderMap1(this, world)
		protagonist.spawn(110.0, 350.0)
		protagonist.setHitboxes(hitboxes)

		hitboxes.addAll(listOf(
			Hitbox(60.0, 0.0, 220.0, 100.0),
			Hitbox(0.0, 120.0, 110.0, 100.0),
			Hitbox(250.0, 100.0, 64.0, 500.0),
			Hitbox(0.0, 393.0, 110.0, 100.0),

		))

		ColisaoManager.setHitboxes(hitboxes)

		//enableDebug(protagonist, ColisaoManager)

		val sceneContainer = sceneContainer
		val porta = Door(sceneContainer, "quarto", Point(150, 500), protagonist)
		val porta2 = Door(sceneContainer, "cozinha", Point(10, 350), protagonist)

		addUpdater {
			porta.update()
			porta2.update()
		}

		val posicao = protagonist.playerPos
		println("Posição do personagem: x=${posicao.x}, y=${posicao.y}")
	}
}