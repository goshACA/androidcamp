package com.example.android_third_task

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.android_third_task.data.User
import com.example.android_third_task.databinding.FragmentGameBinding
import kotlin.random.Random


class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    private val startNumber: Int = 0
    private val endNumber: Int = 0
    private var randomNumber: Int = startNumber

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtNumberInput.setOnClickListener {
            if (it is EditText)
                it.error = null
        }
        binding.btnGameSubmit.setOnClickListener {
            if (checkInput()) {
                val user: User = GameFragmentArgs.fromBundle(arguments!!).user
                if (isWinner(generateRandomNumber(), binding.edtNumberInput.text.toString().toInt())) {
                    findNavController().navigate(
                        GameFragmentDirections.actionGameFragmentToWinFragment2(
                            user, randomNumber
                        )
                    )
                } else findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameOverFragment(
                        user
                    )
                )
            }
        }


    }

    private fun checkInput(): Boolean {
        if (binding.edtNumberInput.text.toString().isEmpty()) {
            binding.edtNumberInput.error = "Please enter a number between $startNumber and $endNumber"
            return false
        }
        return true
    }

    private fun isWinner(generatedNumber: Int, input: Int) = generatedNumber == input

    private fun generateRandomNumber(): Int {
        randomNumber = (startNumber..endNumber).random()
        return randomNumber
    }

}
