package game.personagens

import game.logica.KeyBinds
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*
import korlibs.math.geom.Point
import korlibs.time.seconds

class maeBase(private val container: Container) {
    private lateinit var image: Sprite

    var momPos: Point = Point(0, 0)
        get() = Point(image.x, image.y)

    private lateinit var animationFront: SpriteAnimation
    private lateinit var animationSide: SpriteAnimation
    private lateinit var animationBack: SpriteAnimation
    private val frameWidth = 32
    private val frameHeight = 32

    suspend fun spawn(initialX: Double = 0.0, initialY: Double = 0.0) {
        var x = initialX
        var y = initialY

        val spritemap = resourcesVfs["Mom.png"].readBitmap().toBMP32()
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
        image = container.sprite(animationFront) {
            position(x, y)   // USA AS VARIÁVEIS x e y DEFINIDAS FORA!
            scale = 4.0
            smoothing = false
        }
    }
    fun getPosition(target: Point): Double {
        val dx = momPos.x - target.x
        val dy = momPos.y - target.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}
