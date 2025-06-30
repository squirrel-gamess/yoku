package game.logica

import korlibs.math.geom.Point
import korlibs.math.geom.Rectangle
import kotlin.math.abs

object ProtagonistCollision {
    // SIDE
    var offsetSideX = -17.0
    var offsetSideY = -14.0
    var widthSide   = 10.0
    var heightSide  = 12.0
    // FRONT
    var offsetFrontX = -17.0
    var offsetFrontY = -14.0
    var widthFront   = 16.0
    var heightFront  = 16.0
    // BACK
    var offsetBackX = -17.0
    var offsetBackY = -14.0
    var widthBack   = 16.0
    var heightBack  = 16.0

    fun hitboxAt(
        position: Point,
        facing: Facing,
        scaleX: Double = 1.0,
        scaleY: Double = 1.0
    ): Rectangle {
        val absX = abs(scaleX)
        val (offX, offY, w, h) = when (facing) {
            Facing.SIDE -> {
                val x = if (scaleX >= 0) offsetSideX * absX else -(offsetSideX + widthSide) * absX
                Quad(x, offsetSideY * scaleY, widthSide * absX, heightSide * scaleY)
            }
            Facing.FRONT -> Quad(
                offsetFrontX * scaleX,
                offsetFrontY * scaleY,
                widthFront * absX,
                heightFront * scaleY
            )
            Facing.BACK -> Quad(
                offsetBackX * scaleX,
                offsetBackY * scaleY,
                widthBack * absX,
                heightBack * scaleY
            )
        }
        return Rectangle(position.x + offX, position.y + offY, w, h)
    }
}

private data class Quad<A, B, C, D>(
    val a: A,
    val b: B,
    val c: C,
    val d: D
)