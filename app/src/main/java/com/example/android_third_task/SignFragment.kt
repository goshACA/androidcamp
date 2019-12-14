package com.example.android_third_task

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android_third_task.data.User
import com.example.android_third_task.databinding.FragmentSignBinding


class SignFragment : Fragment() {
    lateinit var binding: FragmentSignBinding
    lateinit var user: User
    var isUserEntered: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign, container, false
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtLastName.setOnClickListener{
            if(it is EditText)
                it.error = null
        }
        binding.txtFirstName.setOnClickListener{
            if(it is EditText)
                it.error = null
        }
        binding.btnSignUp.setOnClickListener {
            if(checkInput()) {
                initUser()
                view.findNavController().navigate(SignFragmentDirections.actionSignFragmentToGameFragment(user))
            }
        }
    }

    private fun initUser() {
        user = User(name = binding.txtFirstName.text.toString(), surname = binding.txtLastName.text.toString())
    }

    private fun checkInput(): Boolean{
        val potentialName: String = binding.txtFirstName.text.toString()
        val potentialSurname: String = binding.txtLastName.text.toString()
        if (potentialName.isEmpty() ||potentialSurname.isEmpty()) {
            if (potentialName.isEmpty() ) {
                binding.txtFirstName.error = "Please enter name"
                isUserEntered = false
            } else {
                binding.txtLastName.error = "Please enter surname"
                isUserEntered = false
            }
        } else isUserEntered = true
        return isUserEntered
    }

  /*  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }*/


}
