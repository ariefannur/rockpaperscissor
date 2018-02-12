package com.platk.rockpaperscissorkotlin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import com.platk.rockpaperscissorkotlin.R
import com.platk.rockpaperscissorkotlin.engine.GameEngine
import com.platk.rockpaperscissorkotlin.engine.GameState

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by ariefmaffrudin on 2/10/18.
 */

class MainActivity : AppCompatActivity(), GameState, View.OnClickListener {


    val gameEngine:GameEngine = GameEngine()
    var score:Int = 0
    var time:Int = 20
    var dialog:AlertDialog? = null
    var curType: type? = null
    var curState: state? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameEngine.init(this)
        tv_info.text = "Siap siap"
        tv_score.text = "Score : $score"
        tv_time.text = "Time : $time"
        generateDialog()

        tv_time.postDelayed({
            gameEngine.start()
            tv_info.text = ""

        }, 1000)

    }

    override fun onStartEngine() {
        btn_paper.setOnClickListener(this)
        btn_rock.setOnClickListener(this)
        btn_scissor.setOnClickListener(this)
        randomEnemy()
        curState = state.ongame
    }

    override fun onUpdate(tm: Long) {
        if(curState == state.ongame) {
            time--
            tv_time.text = "Time : $time"
        }

        if(time == 0){
            dialog!!.setMessage("Score : $score")
            dialog!!.show()
            curState = state.finish
            gameEngine.stop()
            ViewCompat.animate(img_enemy).translationY(-400f).duration = 100
        }
    }

    override fun onFinish() {

    }

    fun generateDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view:View = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null, false)
        builder.setView(view)
        builder.setTitle("Result")
        builder.setMessage("Score akhir : $score")
        builder.setPositiveButton("restart") { dialog, which ->
            dialog.dismiss()
            restart()
        }
        builder.setCancelable(false)
        dialog = builder.create()
    }

    enum class type{
        rock,
        paper,
        scissor
    }

    enum class state{
        ongame,
        finish,
    }

    override fun onClick(v: View) {
        if(curState == state.finish)
            return

      when(v.id){
          R.id.btn_rock->{
              img_mc.setImageResource(R.drawable.ic_rock)
              cekAnswer(type.rock)
          }
          R.id.btn_paper->{
              img_mc.setImageResource(R.drawable.ic_paper)
              cekAnswer(type.paper)
          }
          R.id.btn_scissor->{
              img_mc.setImageResource(R.drawable.ic_scissor)
              cekAnswer(type.scissor)
          }
      }
        ViewCompat.animate(img_mc).translationY(-600f).duration = 100

    }


    fun cekAnswer(tp: type){
        var info:String = ""
       if(curType == type.rock){
           if(tp == type.rock){
               info = "=" //equal
           }else if(tp == type.paper){
               info = ":)" //win
               score+=10
           }else{
               info = ":(" //lose
           }
       }else if(curType == type.paper){
           if(tp == type.rock){
               info = ":)" // lose
           }else if(tp == type.paper){
               info = "=" //equal
           }else{
               info = ":(" //win
               score+=10
           }
       }else{
           if(tp == type.rock){
               info = ":(" // win
               score+=10
           }else if(tp == type.paper){
               info = ":)" // lose
           }else{
               info = "=" // equal
           }
       }

        tv_score.text = "Score : $score"
        tv_info.text = "$info"


        tv_info.postDelayed({
            tv_info.text = ""
            ViewCompat.animate(img_enemy).translationY(-400f).duration = 100
            ViewCompat.animate(img_mc).translationY(600f).duration = 100
            tv_info.postDelayed({
                randomEnemy()
            }, 500)

        }, 500)
    }

    fun randomEnemy(){

        when (random(0, 3)){
                2-> {
                    curType = type.rock
                    img_enemy.setImageResource(R.drawable.ic_rock)
                }
                1-> {
                    curType = type.paper
                    img_enemy.setImageResource(R.drawable.ic_paper)
                }
                0-> {
                    curType = type.scissor
                    img_enemy.setImageResource(R.drawable.ic_scissor)
                }

        }

        ViewCompat.animate(img_enemy).translationY(400f).duration = 100


    }

    fun random(from: Int, to: Int) : Int {
        return Random().nextInt(to - from) + from
    }

    fun restart(){
        score = 0
        time = 20
        curType = null
        curState = null

        gameEngine.init(this)
        gameEngine.start()
    }
}
