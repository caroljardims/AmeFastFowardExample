package com.example.vcviuaplaca.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vcviuaplaca.R
import com.example.vcviuaplaca.ui.game.GameActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameButton.setOnClickListener {
            val nextScene = Intent(this,GameActivity::class.java)
            startActivity(nextScene)
        }
        leaveButton.setOnClickListener {
            finish()
        }
    }
}
