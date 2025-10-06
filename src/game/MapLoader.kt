package game

import korlibs.korge.ldtk.view.LDTKLevelView
import korlibs.korge.view.Container
import korlibs.korge.view.addTo
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.ldtk.view.LDTKWorld
import korlibs.korge.ldtk.view.readLDTKWorld

object MapLoader {
	suspend fun loadWorld(mapName: String): LDTKWorld {
		val file = resourcesVfs["ldtk/$mapName.ldtk"]
		return file.readLDTKWorld()
	}

	fun renderLevel(container: Container, world: LDTKWorld, levelName: String = "quarto", scale: Double = 2.0) {
		// Tente encontrar o nível pelo nome fornecido
		val level = world.levels.firstOrNull { it.level.identifier == levelName }
			?: world.levels.firstOrNull() // Se não encontrar, use o primeiro nível disponível
			?: error("Nenhum nível encontrado no mapa.")

		LDTKLevelView(level).addTo(container).apply {
			this.scale = scale
		}
	}
}

// Funções helper para carregar e renderizar mapas específicos
suspend fun loadMap1(): LDTKWorld = MapLoader.loadWorld("corredor")
fun renderMap1(container: Container, world: LDTKWorld, scale: Double = 4.0) =
    MapLoader.renderLevel(container, world, "corredor", scale) // Mudando para "Level_1" que é um nome comum para o primeiro nível

suspend fun loadMap2(): LDTKWorld = MapLoader.loadWorld("cozinha")
fun renderMap2(container: Container, world: LDTKWorld, scale: Double = 3.0) =
    MapLoader.renderLevel(container, world, "cozinha", scale) // Mudando para "Level_2"

suspend fun loadMap3(): LDTKWorld = MapLoader.loadWorld("casa")
fun renderMap3(container: Container, world: LDTKWorld, scale: Double = 2.0) =
    MapLoader.renderLevel(container, world, "Level_0", scale) // Mudando para "Level_3"
