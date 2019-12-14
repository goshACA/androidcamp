package com.example.android_third_task

import android.content.Context
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android_third_task.databinding.FragmentGameOverBinding


class GameOverFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding: FragmentGameOverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.user = GameOverFragmentArgs.fromBundle(arguments!!).user
        binding.btnRestart.setOnClickListener {
            findNavController().navigate(
                GameOverFragmentDirections.actionGameOverFragmentToGameFragment(
                    GameOverFragmentArgs.fromBundle(arguments!!).user
                )
            )
        }

    }
}
