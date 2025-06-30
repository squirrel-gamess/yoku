import korlibs.event.Key
import korlibs.event.KeyEvent
import korlibs.event.keys
import korlibs.korge.input.keys
import korlibs.korge.view.Stage

object KeyBinds {
    private var stage: Stage? = null

    fun init(stage: Stage) {
        this.stage = stage
    }

    fun left(): Boolean {
        return stage?.keys?.pressing?.contains(Key.LEFT) == true ||
               stage?.keys?.pressing?.contains(Key.A) == true
    }

    fun right(): Boolean {
        return stage?.keys?.pressing?.contains(Key.RIGHT) == true ||
               stage?.keys?.pressing?.contains(Key.D) == true
    }

    fun up(): Boolean {
        return stage?.keys?.pressing?.contains(Key.UP) == true ||
               stage?.keys?.pressing?.contains(Key.W) == true
    }

    fun down(): Boolean {
        return stage?.keys?.pressing?.contains(Key.DOWN) == true ||
               stage?.keys?.pressing?.contains(Key.S) == true
    }
}
