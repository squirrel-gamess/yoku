import korlibs.math.geom.Point
import korlibs.math.geom.Rectangle
import korlibs.korge.view.Container
import game.personagens.Direcao

class Hitbox(
    var x: Double,
    var y: Double,
    var width: Double,
    var height: Double
) {
    fun intersects(other: Hitbox): Boolean {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y
    }

    fun setPosition(newX: Double, newY: Double) {
        x = newX
        y = newY
    }

    fun toBounds(): Rectangle = Rectangle(x, y, width, height)
}

class ProtagonistHitbox(
    private val container: Container,
    width: Double,
    height: Double
) : Hitbox(0.0, 0.0, width, height) {

    private var offsetFrente = Point(0, 0)
    private var offsetCostas = Point(0, 0)
    private var offsetDireita = Point(0, 0)
    private var offsetEsquerda = Point(0, 0)

    fun definirOffsets(
        frente: Point,
        costas: Point,
        direita: Point,
        esquerda: Point
    ) {
        offsetFrente = frente
        offsetCostas = costas
        offsetDireita = direita
        offsetEsquerda = esquerda
    }

    fun verificarColisao(
        protagonistaX: Double,
        protagonistaY: Double,
        hitboxes: List<Hitbox>,
        direcao: Direcao
    ): Boolean {
        // Escolhe o offset correto baseado na direção dominante
        val offset = when (direcao) {
            Direcao.FRENTE -> offsetFrente
            Direcao.COSTAS -> offsetCostas
            Direcao.DIREITA -> offsetDireita
            Direcao.ESQUERDA -> offsetEsquerda
        }

        // Posiciona a hitbox com base na posição do protagonista + offset da direção
        this.setPosition(
            protagonistaX + offset.x,
            protagonistaY + offset.y
        )

        // Verifica colisão com todas as hitboxes do cenário
        return hitboxes.any { this.intersects(it) }
    }

    // Método para debug visual da hitbox
    fun atualizarDebug(x: Double, y: Double, direcao: Direcao) {
        // Implementação opcional para debug visual
    }
}