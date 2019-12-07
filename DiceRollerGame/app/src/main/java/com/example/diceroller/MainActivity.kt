package com.example.diceroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    private val gameStates = HashMap<Int, Int>()
    private var playsCount = 0
    private val maxPlayersCount = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userNameTextView: TextView = findViewById(R.id.txt_user)
        val rollButton: Button = findViewById(R.id.button_roll)
        val diceImageView1: ImageView =
            findViewById<RelativeLayout>(R.id.layout_first_dice).findViewById(R.id.img_view_dice)
        val diceImageView2: ImageView =
            findViewById<RelativeLayout>(R.id.layout_second_dice).findViewById(R.id.img_view_dice)
        val stateTextView1: TextView =
            findViewById<RelativeLayout>(R.id.layout_first_dice).findViewById(R.id.txt_roll_number)
        val stateTextView2: TextView =
            findViewById<RelativeLayout>(R.id.layout_second_dice).findViewById(R.id.txt_roll_number)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)


        configUser(userNameTextView, ++playsCount)

        val resultString = getString(R.string.result)
        val switchPlayer = getString(R.string.switch_player)
        val rollString = getString(R.string.roll)
        rollButton.setOnClickListener {
            when (rollButton.text) {
                resultString -> {
                    rollButton.visibility = View.GONE
                    showResult()
                    return@setOnClickListener
                }
                switchPlayer -> {
                    switchUser(
                        userNameTextView,
                        progressBar
                    )
                    userGameController(
                        diceImageView1,
                        diceImageView2,
                        stateTextView1,
                        stateTextView2,
                        rollRandomPair(),
                        playsCount
                    )
                }
                rollString -> {
                    userGameController(
                        diceImageView1,
                        diceImageView2,
                        stateTextView1,
                        stateTextView2,
                        rollRandomPair(),
                        playsCount
                    )
                    progressAnimation(progressBar)
                }
            }
            rollButton.text = switchPlayer
            if (playsCount >= maxPlayersCount)
                rollButton.text = resultString
        }

    }

    private fun showResult() {
        val winnerEntry: Map.Entry<Int, Int> = gameStates.maxBy { it.value }!!
        txt_result.visibility = View.VISIBLE
        txt_result.text = resources.getString(R.string.winner_message).format(winnerEntry.key, winnerEntry.value)
    }


    private fun configUser(txtView: TextView, playerNumber: Int) {
        txtView.text = resources.getString(R.string.user).format(playerNumber)
        gameStates[playerNumber] = 0
    }

    private fun userGameController(
        diceImageView1: ImageView, diceImageView2: ImageView,
        txtView1: TextView, txtView2: TextView, states: Pair<Int, Int>, playerNumber: Int
    ) {
        rollDice(states.first, diceImageView1)
        rollDice(states.second, diceImageView2)
        txtView1.text = states.first.toString()
        txtView2.text = states.second.toString()
        gameStates[playerNumber] = states.first + states.second
    }

    private fun rollRandomPair(): Pair<Int, Int> {
        val random = Random()
        return Pair(random.nextInt(6) + 1, random.nextInt(6) + 1)
    }

    private fun rollDice(rollNumber: Int, diceImageView: ImageView) {
        when (rollNumber) {
            1 -> diceImageView.setImageResource(R.drawable.dice_1)
            2 -> diceImageView.setImageResource(R.drawable.dice_2)
            3 -> diceImageView.setImageResource(R.drawable.dice_3)
            4 -> diceImageView.setImageResource(R.drawable.dice_4)
            5 -> diceImageView.setImageResource(R.drawable.dice_5)
            6 -> diceImageView.setImageResource(R.drawable.dice_6)
        }
    }


    private fun progressAnimation(progressBar: ProgressBar) {
        Thread(Runnable {
            repeat(10) {
                progressBar.progress += 10
                try {
                    Thread.sleep(100)
                } catch (err: InterruptedException) {

                }
            }
        }).start()
    }

    private fun switchUser(
        userTextView: TextView, progressBar: ProgressBar
    ) {
        progressBar.progress = 0
        progressAnimation(progressBar)

        configUser(userTextView, ++playsCount)
    }


}
