import korlibs.datastructure.*
import korlibs.event.*
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.image.tiles.*
import korlibs.image.tiles.tiled.*
import korlibs.io.file.std.*
import korlibs.korge.*
import korlibs.korge.scene.*
import korlibs.korge.tween.*
import korlibs.korge.view.*
import korlibs.korge.view.filter.*
import korlibs.korge.view.tiles.*
import korlibs.math.geom.*
import korlibs.math.interpolation.*
import korlibs.time.*
import korlibs.korge.tiled.*

suspend fun main() = Korge(windowSize = Size(512, 512), backgroundColor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()

	sceneContainer.changeTo { MyScene() }

}

class MyScene : Scene() {
	override suspend fun SContainer.sceneMain() {

        val spritemap = resourcesVfs["MainSheet.png"].readBitmap().toBMP32()
        val tilemap = resourcesVfs["casa/Casa.tmx"].readTiledMap()


        //tiledmap
        tiledMapView(tilemap)


        val frameWidth = 32
        val frameHeight = 32

        //mapa
        val map = StackedDoubleArray2(20,15)
        for (y in 0 until 15) {
            for (x in 0 until 20) {
                map.push(x,y, 0.0)
            }
        }


        val animationFrente = SpriteAnimation(
            spritemap,
            spriteWidth = frameWidth,
            spriteHeight = frameHeight,
            columns = 8,
            rows = 1
        )

        val animationLado = SpriteAnimation(
            spritemap,
            spriteWidth = frameWidth,
            spriteHeight = frameHeight,
            columns = 8,
            rows = 1,
            marginLeft = 0,
            marginTop = frameHeight
        )

        val image = sprite(animationFrente) {
            position(256, 256)
            scale = 4.0
            smoothing = false
        }


        addUpdater {
            var isMoving = false

            if (input.keys[Key.LEFT] || input.keys[Key.A]) {
                image.playAnimationLooped(animationLado, spriteDisplayTime = 0.07.seconds)
                image.position(image.x - 4, image.y)
                image.scaleX = -4.0 // Virar horizontalmente
                isMoving = true
            }

            if (input.keys[Key.RIGHT] || input.keys[Key.D]) {
                image.playAnimationLooped(animationLado, spriteDisplayTime = 0.07.seconds)
                image.position(image.x + 4, image.y)
                image.scaleX = 4.0 // Garantir orientação normal
                isMoving = true
            }

            if (input.keys[Key.UP] || input.keys[Key.W]) {
                image.playAnimationLooped(animationFrente, spriteDisplayTime = 0.07.seconds)
                image.position(image.x, image.y - 4)
                isMoving = true
            }

            if (input.keys[Key.DOWN] || input.keys[Key.S]) {
                image.playAnimationLooped(animationFrente, spriteDisplayTime = 0.07.seconds)
                image.position(image.x, image.y + 4)
                isMoving = true
            }

            if (!isMoving) {
                // Só pare a animação se nenhuma tecla estiver pressionada
                image.stopAnimation()
                image.setFrame(0)
                }
	        }
        }
    }

