package com.example.game_app

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var btnLogout: Button
    private lateinit var tvPlayerScores: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("GAME_PREFS", MODE_PRIVATE)

        tvUsername = findViewById(R.id.tvUsername)
        btnLogout = findViewById(R.id.btnLogout)
        tvPlayerScores = findViewById(R.id.tvPlayerScores)

        val name = sharedPreferences.getString("NAME", "Tick Tack Toe")
        tvUsername.text = name

//        val scorePlayer1 = intent.getIntExtra("SCORE_PLAYER1", 0)
//        val scorePlayer2 = intent.getIntExtra("SCORE_PLAYER2", 0)

        val highScorePlayer1 = sharedPreferences.getInt("HIGH_SCORE_PLAYER1", 0)
        val highScorePlayer2 = sharedPreferences.getInt("HIGH_SCORE_PLAYER2", 0)

        val playerScoresText =
//            "Player 1 Score: $scorePlayer1\nPlayer 2 Score: $scorePlayer2\n" +
                "High Score Player 1: $highScorePlayer1\nHigh Score Player 2: $highScorePlayer2"
        tvPlayerScores.text = playerScoresText

        btnLogout.setOnClickListener {
            navigateToGameActivity()
        }
    }

    private fun navigateToGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
