package com.tdt4240.game.model

import com.badlogic.gdx.Gdx

/**
 * The settings to the game, e.g. muting or unmuting the sound.
 */
object Settings {
    // TODO dependency injection?
    private var prefs = Gdx.app.getPreferences("TanksCantJumpPreferences")!!

    private const val KEY_SOUND = "sound"

    fun toggleSound() {
        prefs.putBoolean(KEY_SOUND, !(prefs.getBoolean(KEY_SOUND)))
    }

    init {
        // register settings
        registerIfNeeded(KEY_SOUND, true)
    }

    // registers a settings property if it nas not been registered yet
    private fun registerIfNeeded(key: String, default_value: Any){
        if(!prefs.contains(key)){
            val mp = java.util.HashMap<String, Any>()
            mp.put(key, default_value)
            prefs.put(mp)
        }
    }


}
