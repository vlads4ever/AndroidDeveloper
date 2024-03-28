package com.example.userjsongetter.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.userjsongetter.R

import com.example.userjsongetter.databinding.FragmentMainBinding
import com.example.userjsongetter.model.UserModel.UserModel
import kotlinx.coroutines.launch

private const val USER_MODEL = "userModel"

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var userModel: UserModel? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            userModel = it.getParcelable(USER_MODEL)
        }

        if (userModel == null) {
            lifecycleScope.launch {
                try {
                    userModel = viewModel.getJson()
                    updateUi(userModel!!)
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("TAG", e.message.toString())
                }
            }
        } else {
            updateUi(userModel!!)
        }

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                try {
                    userModel = viewModel.getJson()
                    updateUi(userModel!!)
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("TAG", e.message.toString())
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (userModel != null) outState.putParcelable(USER_MODEL, userModel)
        super.onSaveInstanceState(outState)
    }

    private fun updateUi(userModel: UserModel) {
        binding.nameField.text = getString(R.string.user_info,
            userModel.firstName,
            userModel.lastName,
            userModel.birthday?.slice(0..9),
            userModel.email)
        Glide.with(this).load(userModel.photoUrl).fitCenter().into(binding.imageView)
    }
}