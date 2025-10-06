package game.logica

import korlibs.event.Key
import korlibs.korge.view.Views

object KeyBinds {
    // Usamos a referência global ao views
    private lateinit var views: Views

    fun initialize(views: Views) {
        KeyBinds.views = views
    }

    fun left() = views.keys[Key.LEFT] || views.keys[Key.A]
    fun right() = views.keys[Key.RIGHT] || views.keys[Key.D]
    fun up() = views.keys[Key.UP] || views.keys[Key.W]
    fun down() = views.keys[Key.DOWN] || views.keys[Key.S]
    fun action() = views.keys[Key.F]
}