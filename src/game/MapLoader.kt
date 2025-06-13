package game

<<<<<<< Updated upstream
=======
import korlibs.io.file.std.resourcesVfs
>>>>>>> Stashed changes
import korlibs.korge.ldtk.view.readLDTKWorld
import korlibs.korge.ldtk.view.LDTKLevelView
import korlibs.korge.ldtk.view.LDTKWorld
import korlibs.korge.view.Container
import korlibs.korge.view.addTo
<<<<<<< Updated upstream
import korlibs.korge.view.position

object MapPath {
	val file = __KR.KRLdtk.casa.__file // Ou defina o caminho aqui
	val file1 = __KR.KRLdtk.corredor.__file // Ou defina o caminho aqui
}

suspend fun loadMap() = MapPath.file.readLDTKWorld()
suspend fun loadMap1() = MapPath.file1.readLDTKWorld()
=======

object MapPath {
	val file = "ldtk/casa.ldtk" // Defina o caminho correto para o arquivo
	val file1 = "ldtk/corredor.ldtk" // Defina o caminho correto para o arquivo
	val file2 = "ldtk/cozinha.ldtk" // Defina o caminho correto para o arquivo
}

suspend fun loadMap() = resourcesVfs[MapPath.file].readLDTKWorld()
suspend fun loadMap1() = resourcesVfs[MapPath.file1].readLDTKWorld()
suspend fun loadMap2() = resourcesVfs[MapPath.file2].readLDTKWorld()
>>>>>>> Stashed changes

fun renderMap(container: Container, world: LDTKWorld) {
	LDTKLevelView(world.levels.first()).addTo(container).apply {
		scale = 2.0
	}
}
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
fun renderMap1(container: Container, world: LDTKWorld) {
	LDTKLevelView(world.levels.first()).addTo(container).apply {
		scale = 4.0
	}
<<<<<<< Updated upstream
}
=======
}


fun renderMap2(container: Container, world: LDTKWorld) {
	LDTKLevelView(world.levels.first()).addTo(container).apply {
		scale = 3.0
		}
	}
>>>>>>> Stashed changes
