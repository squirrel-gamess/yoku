package game

import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*
import korlibs.korge.input.*
import korlibs.math.geom.Point
import korlibs.time.seconds

class Protagonist(private val container: Container) {
    private lateinit var image: Sprite


    // Removendo a referência à image não inicializada
    var playerPos: Point = Point(0, 0)
        get() = Point(image.x, image.y)

    private lateinit var animationFront: SpriteAnimation
    private lateinit var animationSide: SpriteAnimation
<<<<<<< Updated upstream
=======
    private lateinit var animationBack: SpriteAnimation
>>>>>>> Stashed changes
    private val frameWidth = 32
    private val frameHeight = 32

    suspend fun spawn(initialX: Double = 0.0, initialY: Double = 0.0) {
        var x = initialX
        var y = initialY

        val spritemap = resourcesVfs["MainSheet.png"].readBitmap().toBMP32()
<<<<<<< Updated upstream
=======
       animationBack = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1,
           marginLeft = 0, marginTop = 64
        )

>>>>>>> Stashed changes
        animationFront = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1
        )
        animationSide = SpriteAnimation(
            spritemap, frameWidth, frameHeight, columns = 8, rows = 1,
            marginLeft = 0, marginTop = 32
        )
        image = container.sprite(animationFront) {
            position(x, y)   // USA AS VARIÁVEIS x e y DEFINIDAS FORA!
            scale = 4.0
            smoothing = false
        }
        setupUpdater()
    }


    private fun setupUpdater() {
        container.addUpdater {
            handleMovement()
        }
    }

    private fun handleMovement() {
        var isMoving = false

        if (KeyBinds.left()) {
            image.playAnimationLooped(animationSide, spriteDisplayTime = 0.07.seconds)
            image.position(image.x - 4, image.y)
            image.scaleX = -4.0
            isMoving = true
        }
        if (KeyBinds.right()) {
            image.playAnimationLooped(animationSide, spriteDisplayTime = 0.07.seconds)
            image.position(image.x + 4, image.y)
            image.scaleX = 4.0
            isMoving = true
        }
        if (KeyBinds.up()) {
<<<<<<< Updated upstream
            image.playAnimationLooped(animationFront, spriteDisplayTime = 0.07.seconds)
=======
            image.playAnimationLooped(animationBack, spriteDisplayTime = 0.07.seconds)
>>>>>>> Stashed changes
            image.position(image.x, image.y - 4)
            isMoving = true
        }
        if (KeyBinds.down()) {
            image.playAnimationLooped(animationFront, spriteDisplayTime = 0.07.seconds)
            image.position(image.x, image.y + 4)
            isMoving = true
        }

        if (!isMoving) {
            image.stopAnimation()
            image.setFrame(0)
        }
    }

    fun getPosition(target: Point): Double {
        val dx = playerPos.x - target.x
        val dy = playerPos.y - target.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}