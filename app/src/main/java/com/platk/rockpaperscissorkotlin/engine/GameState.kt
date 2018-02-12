package com.platk.rockpaperscissorkotlin.engine

/**
 * Created by ariefmaffrudin on 2/12/18.
 */
interface GameState {

    fun onStartEngine()
    fun onUpdate(time:Long)
    fun onFinish()
}