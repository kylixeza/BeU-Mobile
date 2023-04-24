package com.exraion.beu.ui.auth.register

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
import com.exraion.beu.databinding.FragmentRegisterBinding
import com.exraion.beu.ui.auth.login.LoginFragment
import com.exraion.beu.ui.main.MainActivity
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.UIState
import com.google.android.material.snackbar.Snackbar
import io.github.tonnyl.light.Light
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    
    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater, container, false)
    
    
    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT
    
    override fun FragmentRegisterBinding.binder() {
        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        activity?.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }
        
        lifecycleScope.launchWhenStarted {
            edtEmail.observeValue { viewModel.setEmail(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtPassword.observeValue { viewModel.setPassword(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtUsername.observeValue { viewModel.setName(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtPhoneNumber.observeValue { viewModel.setPhone(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            edtLocation.observeValue { viewModel.setLocation(it) }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it) {
                    UIState.LOADING -> lottieDialog.show()
                    UIState.SUCCESS -> {
                        lottieDialog.dismiss()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        viewModel.savePrefIsLogin(true)
                        viewModel.savePrefHaveRunAppBefore(true)
                        activity?.finish()
                    }
                    UIState.ERROR -> {
                        lottieDialog.dismiss()
                        binding?.root?.let { Light.error(it, viewModel.message, Snackbar.LENGTH_SHORT).show() }
                    }
                    else -> doNothing()
                }
            }
        }
        
        btnRegister.setOnClickListener {
            viewModel.register()
        }
    }
    
    override fun onBackPressedBehaviour() {
        parentFragmentManager.commit {
            replace(R.id.auth_container, LoginFragment())
        }
    }
    
}