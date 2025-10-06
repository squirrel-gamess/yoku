package game.debug

import korlibs.korge.view.Container
import korlibs.korge.view.addUpdater
import korlibs.korge.view.container
import korlibs.korge.view.solidRect
import korlibs.korge.view.circle
import korlibs.korge.view.xy
import korlibs.image.color.Colors
import game.personagens.Protagonist
import game.logica.ColisaoManager

/**
 * Habilita debug visual para hitboxes do protagonista e obstáculos de colisão.
 */
fun Container.enableDebug(
    protagonist: Protagonist,
    colisaoManager: ColisaoManager
) {
    val debugLayer = container()
    addChild(debugLayer)
    addUpdater {
        debugLayer.removeChildren()
        // hitbox do protagonista (verde)
        val ph = protagonist.hitbox
        debugLayer.solidRect(ph.width.toInt(), ph.height.toInt(), Colors.GREEN.withA(128)) {
            xy(ph.x, ph.y)
        }
        // obstáculos de colisão (vermelho)
        colisaoManager.getObstacles().forEach { rect ->
            debugLayer.solidRect(rect.width.toInt(), rect.height.toInt(), Colors.RED.withA((0.3 * 255).toInt())) {
                xy(rect.x, rect.y)
            }
        }
        // pontos de interação retangulares (amarelo)
        colisaoManager.getInteractables().forEach { rect ->
            debugLayer.solidRect(rect.width.toInt(), rect.height.toInt(), Colors.YELLOW.withA(128)) {
                xy(rect.x, rect.y)
            }
        }
        // pontos de interação circulares (amarelo)
        colisaoManager.getInteractableCircles().forEach { circle ->
            debugLayer.circle(circle.radius, Colors.YELLOW.withA(128)) {
                xy(circle.x - circle.radius, circle.y - circle.radius)
            }
        }
    }
}