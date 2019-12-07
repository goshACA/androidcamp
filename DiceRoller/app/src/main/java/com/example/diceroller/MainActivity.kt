package com.example.diceroller

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.lang.NumberFormatException
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.support.design.widget.TextInputLayout
import android.view.inputmethod.InputMethodManager
import java.util.*


class MainActivity : AppCompatActivity() {
    private val rollStateList = LinkedList<Int>()
    private val resettableStateCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rollStateList.add(resources.getString(R.string.first_state).toInt())

        val enterBtn: Button = findViewById(R.id.btn_enter_roll_number)
        val resetBtn: Button = findViewById(R.id.btn_reset_dice_state)
        val rollNumberEditText: EditText = findViewById(R.id.edit_txt_roll_number)
        val rollNumberTextView: TextView = findViewById(R.id.txt_roll_number)
        val diceImageView: ImageView = findViewById(R.id.img_view_dice)
        val inputErrorLayout: TextInputLayout = findViewById(R.id.txt_input_layout_roll_number)


        enterBtn.setOnClickListener {
            val input = inputController(rollNumberEditText, inputErrorLayout) ?: return@setOnClickListener
            diceGameController(input, rollNumberTextView, diceImageView)
            addState(input)
        }
        resetBtn.setOnClickListener {
            clearErrorMessage(inputErrorLayout)
            clearEditText(rollNumberEditText)
            val state = resetState()
            if (state == null) {
                Toast.makeText(this, resources.getString(R.string.last_state_message), Toast.LENGTH_LONG).show()
            } else {
                diceGameController(state, rollNumberTextView, diceImageView)
            }
        }
        rollNumberEditText.setOnClickListener {
            clearErrorMessage(inputErrorLayout)
        }
    }

    private fun diceGameController(rollNumber: Int, rollNumberTextView: TextView, diceImageView: ImageView) {
        rollNumberTextView.text = rollNumber.toString()
        rollDice(rollNumber, diceImageView)
    }

    private fun inputController(editText: EditText, inputLayout: TextInputLayout): Int? {
        hideKeyBoard()
        val errorMessage = resources.getString(R.string.input_error_message)
        if (editText.text.isEmpty())
            showErrorMessage(inputLayout, errorMessage)
        else {
            try {
                val number = editText.text.toString().toInt()
                if (number in 1..6) {
                    clearEditText(editText)
                    return number
                } else showErrorMessage(inputLayout, errorMessage)
            } catch (error: NumberFormatException) {
                showErrorMessage(inputLayout, errorMessage)
                return null
            }
        }
        return null
    }

    private fun showErrorMessage(textInputLayout: TextInputLayout, errorMessage: String) {
        textInputLayout.isEnabled = true
        textInputLayout.error = errorMessage
    }

    private fun clearErrorMessage(textInputLayout: TextInputLayout) {
        textInputLayout.error = null

    }

    private fun clearEditText(editText: EditText) {
        editText.text = null
    }

    private fun hideKeyBoard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, HIDE_NOT_ALWAYS)
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


    private fun addState(state: Int) {
        if (rollStateList.size > resettableStateCount) {
            rollStateList.removeFirst()
        }
        rollStateList.add(state)
    }

    private fun resetState(): Int? {
        return if (rollStateList.size > 1) {
            rollStateList.removeLast()
            rollStateList.last
        } else null
    }
}
