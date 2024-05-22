package com.example.assignment

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FourthActivity : AppCompatActivity() {

    private var progressPlayer1 = 0
    private var progressPlayer2 = 0

    private lateinit var btn_start: Button
    private lateinit var sb_player1: SeekBar
    private lateinit var sb_player2: SeekBar
    var mediaplayer: MediaPlayer? = null

    val player1 = SelectedAgent.player1 as Agent
    val player2 = SelectedAgent.player2 as Agent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)

        val imgplayer1 = findViewById<ImageView>(R.id.imgplayer1)
        val imgplayer2 = findViewById<ImageView>(R.id.imgplayer2)
        val tvplayer1 = findViewById<TextView>(R.id.tvplayer1)
        val tvplayer2 = findViewById<TextView>(R.id.tvplayer2)
        val imgframe = findViewById<ImageView>(R.id.animation)

        imgplayer1.setImageResource(player1.photo)
        imgplayer2.setImageResource(player2.photo)
        tvplayer1.text = player1.name
        tvplayer2.text = player2.name
        imgframe.setBackgroundResource(R.drawable.animation)
        mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.animation)

        btn_start = findViewById(R.id.btn_start)
        sb_player1 = findViewById(R.id.sb_player1)
        sb_player2 = findViewById(R.id.sb_player2)

        btn_start.setOnClickListener {
            btn_start.isEnabled = false
            progressPlayer1 = 0
            progressPlayer2 = 0
            sb_player1.progress = 0
            sb_player2.progress = 0
            val animation = imgframe.background as AnimationDrawable
            animation.start()
            imgframe.visibility = View.VISIBLE
            mediaplayer?.start()
            mediaplayer?.isLooping = true
            runPlayer1()
            runPlayer2()
        }

        val back=findViewById<Button>(R.id.btn_back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


    private val handler = Handler(Looper.getMainLooper()) { msg ->

        if (msg.what == 1)
            sb_player1.progress = progressPlayer1

        if (progressPlayer1 >= 100 && progressPlayer2 < 100) {
            val imgframe = findViewById<ImageView>(R.id.animation)
            imgframe.visibility = View.INVISIBLE
            mediaplayer?.stop()
            showToast("${player1.name} Win!")
            btn_start.isEnabled = true
        }
        true
    }
    //use Thread for Hare
    private fun runPlayer1() {
        Thread {

            val sleepProbability = arrayOf(true, true, false)
            while (progressPlayer1 < 100 && progressPlayer2 < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability.random())
                        Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressPlayer1 += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }
    //use Coroutines for Tortoise
    private fun runPlayer2() {
        GlobalScope.launch(Dispatchers.Main) {
            while (progressPlayer2 < 100 && progressPlayer1 < 100) {
                delay(100)
                progressPlayer2 += 1
                sb_player2.progress = progressPlayer2
                if (progressPlayer2 >= 100 && progressPlayer1 < 100) {
                    val imgframe = findViewById<ImageView>(R.id.animation)
                    imgframe.visibility = View.INVISIBLE
                    mediaplayer?.stop()
                    showToast("${player2.name} Win!")
                    btn_start.isEnabled = true
                }
            }
        }
    }
}