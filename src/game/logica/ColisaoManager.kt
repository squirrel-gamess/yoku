// src/game/logica/ColisaoManager.kt
package game.logica

import korlibs.korge.view.Container
import korlibs.korge.view.View
import korlibs.math.geom.Point
import korlibs.math.geom.Rectangle
import game.personagens.Direcao
import game.logica.Facing

// Interface padrão para Hitbox para desacoplar da implementação específica de BedroomCollision.Hitbox
interface IHitbox {
    val x: Double
    val y: Double
    val width: Double
    val height: Double
}

// Objeto global ColisaoManager - agora é um singleton que pode ser acessado de qualquer lugar
object ColisaoManager {
    private val obstacles = mutableListOf<Rectangle>()
    private val interactables = mutableListOf<Rectangle>()
    private val interactableCircles = mutableListOf<InteractableCircle>()
    var hitbox = Rectangle()
    private var currentContainer: Container? = null

    // Método para definir o container atual - chamado quando mudar de ambiente
    fun setContainer(container: Container) {
        currentContainer = container
        interactables.clear()
        interactableCircles.clear()
    }

    // Define as hitboxes do ambiente atual, aceitando qualquer tipo que implemente IHitbox
    fun setHitboxes(hitboxes: List<IHitbox>) {
        obstacles.clear()
        hitboxes.forEach {
            obstacles += Rectangle(it.x, it.y, it.width, it.height)
        }
    }

    // Método alternativo para aceitar diretamente Rectangles
    fun setHitboxesFromRectangles(rectangles: List<Rectangle>) {
        obstacles.clear()
        obstacles.addAll(rectangles)
    }

    // Atualiza a hitbox do personagem conforme posição do sprite e offsets fixos
    fun atualizarHitbox(view: View, direcao: Direcao) {
        val facing = when (direcao) {
            Direcao.DIREITA, Direcao.ESQUERDA -> Facing.SIDE
            Direcao.FRENTE                   -> Facing.FRONT
            Direcao.COSTAS                   -> Facing.BACK
        }
        hitbox = ProtagonistCollision.hitboxAt(
            Point(view.x, view.y),
            facing,
            view.scaleX,
            view.scaleY
        )
    }

    // For debug: expose environment obstacles
    fun getObstacles(): List<Rectangle> = obstacles

    // For debug: expose interactive zones (pontos de ação)
    fun getInteractables(): List<Rectangle> = interactables
    // For debug: expose interactive circles (pontos de ação circulares)
    fun getInteractableCircles(): List<InteractableCircle> = interactableCircles

    // Processa movimento com verificação de colisão por eixo
    fun processarMovimento(
        view: View,
        currentDirecao: Direcao,
        deltaX: Double,
        deltaY: Double
    ): ColisaoResult {
        // Offsets between hitbox and view, to reposition hitbox after axis moves
        val offX = hitbox.x - view.x
        val offY = hitbox.y - view.y
        var newX = view.x
        var newY = view.y
        var direcao = currentDirecao

        // Salvamos a hitbox original para restaurar caso necessário
        val originalHitbox = Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height)

        // Testa movimento horizontal
        if (deltaX != 0.0) {
            val testBoxX = Rectangle(hitbox.x + deltaX, hitbox.y, hitbox.width, hitbox.height)
            val collidingX = obstacles.filter { it.intersects(testBoxX) }
            if (collidingX.isEmpty()) {
                newX += deltaX
            } else {
                // Ajusta posição para ficar encostado na borda do obstáculo
                val obstacle = if (deltaX > 0) collidingX.minByOrNull { it.x }!! else collidingX.maxByOrNull { it.x }!!
                newX = if (deltaX > 0) {
                    obstacle.x - hitbox.width - offX
                } else {
                    obstacle.x + obstacle.width - offX
                }
            }
            // Atualiza direção visual mesmo se bloqueado
            direcao = if (deltaX > 0) Direcao.DIREITA else Direcao.ESQUERDA
        }

        // Testa movimento vertical
        if (deltaY != 0.0) {
            // Cria uma pequena margem de tolerância para evitar que o personagem fique preso
            val tolerance = 2.0

            // Recalcula a hitbox baseada na nova posição X antes de testar movimento vertical
            // Usando uma largura ligeiramente menor para evitar que fique preso nas quinas
            val adjustedHitboxX = Rectangle(
                newX + offX + tolerance,
                hitbox.y,
                hitbox.width - (tolerance * 2),
                hitbox.height
            )

            val testBoxY = Rectangle(
                adjustedHitboxX.x,
                adjustedHitboxX.y + deltaY,
                adjustedHitboxX.width,
                hitbox.height
            )

            val collidingY = obstacles.filter { it.intersects(testBoxY) }
            if (collidingY.isEmpty()) {
                newY += deltaY
            } else {
                // Ajusta posição para ficar encostado na borda do obstáculo
                val obstacle = if (deltaY > 0) collidingY.minByOrNull { it.y }!! else collidingY.maxByOrNull { it.y }!!
                newY = if (deltaY > 0) {
                    obstacle.y - hitbox.height - offY
                } else {
                    obstacle.y + obstacle.height - offY
                }
            }
            // Atualiza direção visual mesmo se bloqueado
            direcao = if (deltaY > 0) Direcao.FRENTE else Direcao.COSTAS
        }

        // Verificação final: garante que a posição final não tenha colisão
        val finalHitbox = Rectangle(newX + offX, newY + offY, hitbox.width, hitbox.height)
        if (obstacles.any { it.intersects(finalHitbox) }) {
            // Se houve colisão na posição final, restauramos a posição original
            return ColisaoResult(view.x, view.y, currentDirecao)
        }

        return ColisaoResult(newX, newY, direcao)
    }

    fun definirHitboxPersonalizada(novaHitbox: Rectangle) {
        this.hitbox = novaHitbox
    }

    // Adiciona área interativa
    fun addInteractable(rectangle: Rectangle) {
        interactables += rectangle
    }
    // Adiciona área interativa circular
    fun addInteractableCircle(x: Double, y: Double, radius: Double) {
        interactableCircles += InteractableCircle(x, y, radius)
    }

    // Limpa obstáculos
    fun limparObstaculos() {
        obstacles.clear()
    }

    data class ColisaoResult(val x: Double, val y: Double, val direcao: Direcao)
    // Representação de zona interativa circular
    data class InteractableCircle(val x: Double, val y: Double, val radius: Double)
}
