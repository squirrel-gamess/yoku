package game.ui

import korlibs.image.color.Colors
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*

class DialogoAnimacao(private val container: Container) {
    private var root: Container? = null
    private var bgImage: Image? = null
    private var textView: Text? = null
    private var portraitSprite: Sprite? = null
    private var talkAnim: SpriteAnimation? = null

    suspend fun spawn(centerX: Double, centerY: Double) {
        if (root != null) return
        val rootContainer = container.container()
        root = rootContainer

        val spritebg = try { resourcesVfs["BackgroundDialogos.png"].readBitmap().toBMP32() } catch (_: Throwable) { null }
        if (spritebg != null) {
            bgImage = rootContainer.image(spritebg) {
                anchor(0.5, 0.5)
                position(centerX, centerY)
                scale = 4.0
                smoothing = false
            }
        } else {
            rootContainer.solidRect(400, 96, Colors.DARKGREY.withA(0xCC)) {
                anchor(0.5, 0.5)
                position(centerX, centerY)
            }
        }

        textView = rootContainer.text("", textSize = 16.0, color = Colors.WHITE) {
            position(centerX - 180, centerY - 10)
        }

        val spritemae = try { resourcesVfs["MãeFala.png"].readBitmap().toBMP32() } catch (_: Throwable) { null }
        if (spritemae != null) {
            talkAnim = SpriteAnimation(
                spriteMap = spritemae,
                spriteWidth = 128,
                spriteHeight = 128,
                columns = 8,
                rows = 1,
                marginLeft = 0,
                marginTop = 64
            )
            portraitSprite = rootContainer.sprite(talkAnim!!) {
                anchor(0.5, 1.0)
                position(centerX - 220, centerY + 40)
                scale = 4.0
                smoothing = false
                // No animation by default; it will start on mostrarFala()
            }
        }
    }

    fun mostrarFala(personagem: String, fala: String) {
        textView?.text = "$personagem: $fala"
        if (portraitSprite != null && talkAnim != null) {
            portraitSprite!!.playAnimation(talkAnim!!)
        }
    }

    fun limpar() {
        root?.removeFromParent()
        root = null
        bgImage = null
        textView = null
        portraitSprite = null
        talkAnim = null
    }
}
