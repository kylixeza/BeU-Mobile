package com.exraion.beu.ui.auth.login

import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.common.observeValue
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.databinding.FragmentLoginBinding
import com.exraion.beu.ui.auth.register.RegisterFragment
import com.exraion.beu.ui.main.MainActivity
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import com.google.android.material.snackbar.Snackbar
import io.github.tonnyl.light.Light
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    
    private val viewModel by viewModel<LoginViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater, container, false)
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentLoginBinding.binder() {
        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        activity?.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }
        
        lifecycleScope.launchWhenStarted {
            edtEmail.observeValue { viewModel.setEmail(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtPassword.observeValue { viewModel.setPassword(it) }
        }
        
        btnLogin.setOnClickListener {
            viewModel.login()
        }
    
        tvRegister.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.auth_container, RegisterFragment())
            }
        }
        
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    
                    UIState.LOADING -> {
                        lottieDialog.show()
                    }
                    
                    UIState.SUCCESS -> {
                        lottieDialog.dismiss()
                        requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
                        activity?.finish()
                    }
                    
                    UIState.ERROR -> {
                        lottieDialog.dismiss()
                        Light.error(requireView(), viewModel.message, Snackbar.LENGTH_LONG).show()
                    }
                    
                    UIState.EMPTY -> doNothing()
                }
            }
        }
    }
    
    override fun onBackPressedBehaviour() {
        activity?.finish()
    }
    
}