package com.example.android_third_task

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.android_third_task.databinding.FragmentWinBinding


class WinFragment : Fragment() {
    lateinit var binding: FragmentWinBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = WinFragmentArgs.fromBundle(
            arguments!!
        ).user


        binding.btnPlayAgain.setOnClickListener {
            findNavController().navigate(
                WinFragmentDirections.actionWinFragment2ToGameFragment(
                    WinFragmentArgs.fromBundle(
                        arguments!!
                    ).user
                )
            )
        }

        binding.btnShareWinNumber.setOnClickListener {
                shareSuccess()
        }

    }

    private fun getShareIntent(): Intent {
        val args = WinFragmentArgs.fromBundle(arguments!!)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, args.winNumber.toString())
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

}