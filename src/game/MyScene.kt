package game

import korlibs.image.color.RGBA
import korlibs.korge.Korge
import korlibs.korge.scene.sceneContainer

suspend fun main() = Korge(
    windowWidth = 1024,
    windowHeight = 768,
    backgroundColor = RGBA(0x222222FF)
) {
    // Usa a função de extensão sceneContainer() do Stage
    sceneContainer {
        // Dentro deste contexto, changeTo é uma função suspensa chamada corretamente
        changeTo<Quarto>()
    }
}