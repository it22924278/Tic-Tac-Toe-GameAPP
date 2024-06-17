package com.example.game_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var but0: Button
    private lateinit var but1: Button
    private lateinit var but2: Button
    private lateinit var but3: Button
    private lateinit var but4: Button
    private lateinit var but5: Button
    private lateinit var but6: Button
    private lateinit var but7: Button
    private lateinit var but8: Button

    private lateinit var tv: TextView
    private var player1 = 0
    private var player2 = 1
    private var activePlayer = player1
    private lateinit var filledPos: IntArray

    private var gameActive = true
    private var scorePlayer1 = 0
    private var scorePlayer2 = 0

    private var roundCount = 0
    private var maxRounds = 2

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        sharedPreferences = getSharedPreferences("GAME_PREFS", MODE_PRIVATE)

        filledPos = IntArray(9) { -1 }

        tv = findViewById(R.id.textView2)
        but0 = findViewById(R.id.bb0)
        but1 = findViewById(R.id.bb1)
        but2 = findViewById(R.id.bb2)
        but3 = findViewById(R.id.bb3)
        but4 = findViewById(R.id.bb4)
        but5 = findViewById(R.id.bb5)
        but6 = findViewById(R.id.bb6)
        but7 = findViewById(R.id.bb7)
        but8 = findViewById(R.id.bb8)

        but0.setOnClickListener(this)
        but1.setOnClickListener(this)
        but2.setOnClickListener(this)
        but3.setOnClickListener(this)
        but4.setOnClickListener(this)
        but5.setOnClickListener(this)
        but6.setOnClickListener(this)
        but7.setOnClickListener(this)
        but8.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!gameActive)
            return

        val btnClicked = findViewById<Button>(v!!.id)
        val clickedTag = v.tag.toString().toInt()

        if (filledPos[clickedTag] != -1)
            return

        filledPos[clickedTag] = activePlayer

        if (activePlayer == player1) {
            btnClicked.text = "0"
            activePlayer = player2
            tv.text = "Player-2 Turn"
            btnClicked.setTextColor(Color.BLACK)
            btnClicked.backgroundTintList = getColorStateList(R.color.yellow)
        } else {
            btnClicked.text = "x"
            activePlayer = player1
            tv.text = "Player-1 Turn"
            btnClicked.setTextColor(Color.BLACK)
            btnClicked.backgroundTintList = getColorStateList(R.color.green)
        }

        checkForWin()
    }

    private fun checkForWin() {
        val winpos = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )

        for (i in winpos.indices) {
            val (val0, val1, val2) = winpos[i]

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    gameActive = false
                    if (filledPos[val0] == player1) {
                        scorePlayer1++
                        tv.text = " Score: Player-1: $scorePlayer1, Player-2: $scorePlayer2"
                        showMessage("Player-1 is winner")
                    } else {
                        scorePlayer2++
                        tv.text = "Score: Player-1: $scorePlayer1, Player-2: $scorePlayer2"
                        showMessage("Player-2 is winner")
                    }
                    return
                }
            }
        }

        var count = 0
        for (i in filledPos) {
            if (i == -1) {
                count++
            }
        }
        if (count == 0) {
            showMessage("It's Draw")
            return
        }
    }

    private fun showMessage(s: String) {
        AlertDialog.Builder(this)
            .setMessage(s)
            .setTitle("Tic Tac Toe")
            .setPositiveButton("OK") { dialog, which -> restartGame() }
            .show()
    }

    private fun restartGame() {
        roundCount++
        if (roundCount > maxRounds) {
            saveHighScores()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("SCORE_PLAYER1", scorePlayer1)
            intent.putExtra("SCORE_PLAYER2", scorePlayer2)
            startActivity(intent)
            finish()
        } else {
            filledPos = IntArray(9) { -1 }
            activePlayer = player1
            gameActive = true
            tv.text = "Player-1 Turn"
            val buttons = arrayOf(but0, but1, but2, but3, but4, but5, but6, but7, but8)
            for (button in buttons) {
                button.text = ""
                button.backgroundTintList = getColorStateList(com.google.android.material.R.color.design_default_color_primary)
            }
        }
    }

    private fun saveHighScores() {
        val editor = sharedPreferences.edit()
        editor.putInt("HIGH_SCORE_PLAYER1", scorePlayer1)
        editor.putInt("HIGH_SCORE_PLAYER2", scorePlayer2)
        editor.apply()
    }
}
