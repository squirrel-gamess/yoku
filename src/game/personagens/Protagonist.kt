package game.personagens

import game.logica.*
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*
import korlibs.math.geom.Point
import korlibs.time.seconds

class Protagonist(private val container: Container) {
    lateinit var image: Sprite

    // Dimensões de cada frame na spritesheet
    private val frameWidth = 32
    private val frameHeight = 32

    // Sistema de coordenadas unificado
    private val scale = 4.0

    // Gerenciador de colisões (singleton global)
    private val colisaoManager = ColisaoManager

    // Acesso público ao hitbox
    val hitbox get() = colisaoManager.hitbox

    // Direções separadas: uma para animação (visual) e outra para colisão
    private var direcaoAtual = Direcao.FRENTE
    private var direcaoAnim = Direcao.FRENTE

    var playerPos: Point = Point(0, 0)
        get() = Point(image.x, image.y)
        set(value) {
            field = value
            if (::image.isInitialized) {
                image.position(value.x, value.y)
                atualizarHitboxPersonalizada()
            }
        }

    private lateinit var animationFront: SpriteAnimation
    private lateinit var animationSide: SpriteAnimation
    private lateinit var animationBack: SpriteAnimation
    private lateinit var animationSide2: SpriteAnimation

    // Define hitboxes do ambiente no ColisaoManager global
    fun setHitboxes(hitboxes: List<IHitbox>) {
        colisaoManager.setHitboxes(hitboxes)
    }

    // Método para atualizar a hitbox usando ProtagonistCollision
    private fun atualizarHitboxPersonalizada() {
        if (::image.isInitialized) {
            val facing = when (direcaoAtual) {
                Direcao.ESQUERDA, Direcao.DIREITA -> Facing.SIDE
                Direcao.FRENTE                   -> Facing.FRONT
                Direcao.COSTAS                   -> Facing.BACK
            }
            val hitbox = ProtagonistCollision.hitboxAt(
                Point(image.x, image.y),
                facing,
                image.scaleX,
                image.scaleY
            )
            colisaoManager.definirHitboxPersonalizada(hitbox)
        }
    }

    suspend fun spawn(initialX: Double = 0.0, initialY: Double = 0.0) {
        val spritemap = resourcesVfs["MainSheet.png"].readBitmap().toBMP32()

        animationBack = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1,
            marginLeft = 0, marginTop = 64
        )

        animationFront = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1
        )

        animationSide = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1,
            marginLeft = 0, marginTop = 32
        )

        animationSide2 = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1,
            marginLeft = 0, marginTop = 96
        )

        image = container.sprite(animationFront) {
            position(initialX, initialY)
            anchor(.5, 1.0)
            scale = this@Protagonist.scale
            smoothing = false
        }
        // Atualiza a hitbox usando as configurações personalizadas
        atualizarHitboxPersonalizada()

        val obstacles = colisaoManager.getObstacles()
        if (obstacles.any { it.intersects(colisaoManager.hitbox) }) {
            fun findNearestFree(x0: Double, y0: Double): Point {
                val step = 4.0
                var radius = step
                while (radius <= 200) {
                    val count = (radius * 2 / step).toInt()
                    // Horizontal edges
                    for (i in -count..count) {
                        val dx = i * step
                        for (dir in listOf(-1.0, 1.0)) {
                            val nx = x0 + dx
                            val ny = y0 + radius * dir
                            image.position(nx, ny)
                            atualizarHitboxPersonalizada()
                            if (obstacles.none { it.intersects(colisaoManager.hitbox) }) return Point(nx, ny)
                        }
                    }
                    // Vertical edges
                    for (i in -count..count) {
                        val dy = i * step
                        for (dir in listOf(-1.0, 1.0)) {
                            val nx = x0 + radius * dir
                            val ny = y0 + dy
                            image.position(nx, ny)
                            atualizarHitboxPersonalizada()
                            if (obstacles.none { it.intersects(colisaoManager.hitbox) }) return Point(nx, ny)
                        }
                    }
                    radius += step
                }
                return Point(x0, y0)
            }
            val newPos = findNearestFree(initialX, initialY)
            image.position(newPos.x, newPos.y)
            atualizarHitboxPersonalizada()
        }
        setupUpdater()
    }

    private fun setupUpdater() {
        container.addUpdater { handleMovement() }
    }

    private fun handleMovement() {
        var isMoving = false
        var deltaX = 0.0
        var deltaY = 0.0
        val velocidade = 4.0
        var novaDirecao = direcaoAtual // Armazenar a nova direção antes de aplicá-la

        // Tratamento de movimento horizontal - sem espelhamento de sprite
        if (KeyBinds.right()) {
            deltaX = velocidade
            novaDirecao = Direcao.DIREITA
            image.playAnimationLooped(animationSide, spriteDisplayTime = 0.07.seconds)
            isMoving = true
        } else if (KeyBinds.left()) {
            deltaX = -velocidade
            novaDirecao = Direcao.ESQUERDA
            image.playAnimationLooped(animationSide2, spriteDisplayTime = 0.07.seconds)
            isMoving = true
        }

        // Tratamento de movimento vertical - usando a mesma lógica que o horizontal
        // com else-if para priorizar uma direção e evitar conflitos
        if (KeyBinds.up()) {
            deltaY = -velocidade
            novaDirecao = Direcao.COSTAS
            image.playAnimationLooped(animationBack, spriteDisplayTime = 0.07.seconds)
            isMoving = true
        } else if (KeyBinds.down()) {
            deltaY = velocidade
            novaDirecao = Direcao.FRENTE
            image.playAnimationLooped(animationFront, spriteDisplayTime = 0.07.seconds)
            isMoving = true
        }

        if (!isMoving) {
            image.stopAnimation()
            image.setFrame(0)
            return
        }

        // Verificar se está usando mais de uma direção (movimento diagonal)
        val movimentoDiagonal = deltaX != 0.0 && deltaY != 0.0

        // Não alterar a direção imediatamente para evitar mudanças na hitbox durante movimento
        // Atualiza hitbox antes de checar colisão
        atualizarHitboxPersonalizada()

        // Processando movimentos de forma segura para evitar ficar preso em colisões

        // Primeiro processamos o movimento horizontal
        if (deltaX != 0.0) {
            val resultadoX = colisaoManager.processarMovimento(image, direcaoAtual, deltaX, 0.0)

            // Só atualizamos a posição se o movimento for possível
            if (resultadoX.x != image.x) {
                image.position(resultadoX.x, image.y)
                if (!movimentoDiagonal) {
                    direcaoAtual = resultadoX.direcao
                }
                atualizarHitboxPersonalizada()
            }
        }

        // Depois processamos o movimento vertical
        if (deltaY != 0.0) {
            val resultadoY = colisaoManager.processarMovimento(image, direcaoAtual, 0.0, deltaY)

            // Só atualizamos a posição se o movimento for possível
            if (resultadoY.y != image.y) {
                image.position(image.x, resultadoY.y)
                if (!movimentoDiagonal || (movimentoDiagonal && deltaX == 0.0)) {
                    direcaoAtual = resultadoY.direcao
                }
                atualizarHitboxPersonalizada()
            }
        }

        // Só atualizamos a direção depois de ambos os movimentos terem sido processados
        // para evitar mudanças de hitbox durante o movimento
        if (isMoving) {
            direcaoAtual = novaDirecao
            atualizarHitboxPersonalizada()

            // Verificação final para garantir que não estamos dentro de uma colisão
            val obstacles = colisaoManager.getObstacles()
            if (obstacles.any { it.intersects(colisaoManager.hitbox) }) {
                // Se a hitbox atual colide com algo, voltamos à direção anterior
                direcaoAtual = novaDirecao
                atualizarHitboxPersonalizada()
            }
        }
    }
}
