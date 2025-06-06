package game

import korlibs.korge.ldtk.view.readLDTKWorld
import korlibs.korge.ldtk.view.LDTKLevelView
import korlibs.korge.ldtk.view.LDTKWorld
import korlibs.korge.view.Container
import korlibs.korge.view.addTo
import korlibs.korge.view.position

object MapPath {
	val file = __KR.KRLdtk.casa.__file // Ou defina o caminho aqui
	val file1 = __KR.KRLdtk.corredor.__file // Ou defina o caminho aqui
}

suspend fun loadMap() = MapPath.file.readLDTKWorld()
suspend fun loadMap1() = MapPath.file1.readLDTKWorld()

fun renderMap(container: Container, world: LDTKWorld) {
	LDTKLevelView(world.levels.first()).addTo(container).apply {
		scale = 2.0
	}
}
fun renderMap1(container: Container, world: LDTKWorld) {
	LDTKLevelView(world.levels.first()).addTo(container).apply {
		scale = 4.0
	}
}