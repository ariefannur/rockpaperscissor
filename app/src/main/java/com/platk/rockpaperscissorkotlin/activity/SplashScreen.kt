package com.platk.rockpaperscissorkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.platk.rockpaperscissorkotlin.R

/**
 * Created by ariefmaffrudin on 2/10/18.
 */
class SplashScreen :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splash)


        val view:ImageView = findViewById(R.id.img_splash)
        view.postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}