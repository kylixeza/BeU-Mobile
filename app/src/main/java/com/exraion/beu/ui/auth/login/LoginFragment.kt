package com.exraion.beu.ui.auth.login

import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.exraion.beu.ui.main.MainActivity
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.databinding.FragmentLoginBinding
import com.exraion.beu.ui.auth.register.RegisterFragment
import com.exraion.beu.util.Constanta
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import com.google.android.material.snackbar.Snackbar
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.textChanges

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
            edtEmail.textChanges()
                .skipInitialValue()
                .map { it.toString() }
                .collectLatest { viewModel.setEmail(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtPassword.textChanges()
                .skipInitialValue()
                .map { it.toString() }
                .collectLatest { viewModel.setPassword(it) }
        }
        
        btnLogin.setOnClickListener {
            viewModel.login()
        }
    
        tvRegister.setOnClickListener {
            if (Constanta.SOURCE == Constanta.SOURCE_LOGOUT) {
                parentFragmentManager.commit {
                    replace(R.id.auth_container, RegisterFragment())
                }
            } else {
                view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when(it) {
                    UIState.IDLE -> doNothing()
                    
                    UIState.LOADING -> {
                        lottieDialog.show()
                    }
                    
                    UIState.SUCCESS -> {
                        lottieDialog.dismiss()
                        if (Constanta.SOURCE == Constanta.SOURCE_LOGOUT)
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                        else
                            view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginDestinationToMainDestination())
                        viewModel.savePrefIsLogin(true)
                        viewModel.savePrefHaveRunAppBefore(true)
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
        if (Constanta.SOURCE == Constanta.SOURCE_LOGOUT) {
            activity?.finish()
        } else {
            view?.findNavController()?.navigate(
                LoginFragmentDirections.actionLoginDestinationToOnBoardingDestination("Login")
            )
        }
    }
    
}