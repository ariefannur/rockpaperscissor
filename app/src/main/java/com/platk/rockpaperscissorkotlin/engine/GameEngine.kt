package com.platk.rockpaperscissorkotlin.engine

import android.os.CountDownTimer
import java.util.*

/**
 * Created by ariefmaffrudin on 2/10/18.
 */
class GameEngine {

    val TIME_UNIT: Long = 21000
    var timer:CountDownTimer? = null

    fun init(state: GameState){
        state.onStartEngine()
        timer = object : CountDownTimer(TIME_UNIT, 1000){

            override fun onFinish() {
                state.onFinish()
            }

            override fun onTick(p0: Long) {
                state.onUpdate(p0)
            }
        }
    }

    fun start(){
        timer?.start()
    }

    fun stop(){
        timer?.cancel()
    }


}