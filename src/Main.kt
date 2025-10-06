import korlibs.image.color.Colors
import korlibs.korge.Korge
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size

suspend fun main() = Korge(
    windowSize = Size(1280, 720),        // tamanho da janela
    virtualSize = Size(1280, 720),       // resolução lógica (mundo do jogo)
    backgroundColor = Colors["#2b2b2b"]
) {
    val sceneContainer = sceneContainer()
    sceneContainer.changeTo { menu() }
}



