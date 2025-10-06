package game.ambientes.Kitchen

import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.view.*
import korlibs.time.TimeSpan

class DialogoAnimacao {
    private var falaSprite: Sprite? = null
    private var falaAnim: SpriteAnimation? = null
    private var bg: Image? = null

    private val frameWidth = 128
    private val frameHeight = 128

    /**
     * Spawna a animação de fala no **UIContainer**
     * para não ser afetada pela câmera do jogo
     */
    suspend fun spawnDialogo(uiContainer: Container) {
        if (falaSprite != null) return

        val spritemae = resourcesVfs["MaeFala.png"].readBitmap().toBMP32()
        val background = resourcesVfs["BackgroundDialogos.png"].readBitmap().toBMP32()

        val stage = uiContainer.stage ?: return
        val centerX = stage.views.virtualWidth / 2.0
        val bottomY = stage.views.virtualHeight - 40.0

        bg = uiContainer.image(background) {
            anchor(0.5, 1.0)
            position(centerX, bottomY)
            scale(2.5, 1.5)
            smoothing = false
        }

        val animation = SpriteAnimation(
            spriteMap = spritemae,
            spriteWidth = frameWidth,
            spriteHeight = frameHeight,
            columns = 5
        )
        falaAnim = animation

        falaSprite = uiContainer.sprite(animation) {
            anchor(0.5, 1.0)
            position(300, 350)
            scale = 3.0
            smoothing = false
            playAnimationLooped(animation)
        }
    }

    /**
     * 🔁 Troca a imagem do sprite de fala conforme o JSON
     */
    suspend fun trocarImagem(novoArquivo: String, _duracao: TimeSpan, colunas: Int = 5) {
        val sprite = falaSprite ?: return

        val novoBitmap = resourcesVfs[novoArquivo].readBitmap().toBMP32()
        val novaAnimacao = SpriteAnimation(
            spriteMap = novoBitmap,
            spriteWidth = frameWidth,
            spriteHeight = frameHeight,
            columns = colunas
        )

        falaAnim = novaAnimacao
        sprite.playAnimationLooped(novaAnimacao)
    }

    fun animarFala(_duracao: TimeSpan) {
        val sprite = falaSprite ?: return
        val animacao = falaAnim ?: return
        sprite.playAnimationLooped(animacao)
    }

    /** Remove o sprite e o background da tela (fim do diálogo) */
    fun remove() {
        falaSprite?.removeFromParent()
        falaSprite = null
        falaAnim = null

        bg?.removeFromParent()
        bg = null
    }
}
