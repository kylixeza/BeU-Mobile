package com.exraion.beu.ui.profile

import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.adapter.profile_additional_settings.ProfileAdditionalSettingAdapter
import com.exraion.beu.adapter.profile_additional_settings.ProfileAdditionalSettingListener
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.buildLottieDialog
import com.exraion.beu.common.initLinearVertical
import com.exraion.beu.databinding.DialogLottieBinding
import com.exraion.beu.databinding.FragmentProfileBinding
import com.exraion.beu.ui.auth.AuthActivity
import com.exraion.beu.util.AdditionalSettingConfig
import com.exraion.beu.util.ScreenOrientation
import com.exraion.beu.util.isErrorDo
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import io.github.tonnyl.light.Light
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    
    private val viewModel by viewModel<ProfileViewModel>()
    private val accountAdditionalSettingsAdapter by inject<ProfileAdditionalSettingAdapter>()
    private val moreInfoAdditionalSettingAdapter by inject<ProfileAdditionalSettingAdapter>()

    private lateinit var lottieBinding: DialogLottieBinding
    private lateinit var lottieDialog: Dialog
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentProfileBinding.binder() {

        lottieBinding = DialogLottieBinding.inflate(layoutInflater)
        activity?.apply { lottieDialog = buildLottieDialog(lottieBinding, "loading_state.json") }
        
        rvAccountSettings.initLinearVertical(requireContext(), accountAdditionalSettingsAdapter)
        rvMoreInfoSettings.initLinearVertical(requireContext(), moreInfoAdditionalSettingAdapter)
        
        lifecycleScope.launch {
            viewModel.user.collect {
                Glide.with(requireContext())
                    .load(it?.avatar)
                    .placeholder(R.drawable.ilu_default_profile_picture)
                    .into(ivProfile)
    
                tvName.text = it?.name
                tvEmail.text = it?.email
            }
        }
        
        lifecycleScope.launch {
            viewModel.accountAdditionalSettings.collectLatest {
                accountAdditionalSettingsAdapter.submitList(it)
            }
        }
        
        lifecycleScope.launch {
            viewModel.moreInfoAdditionalSettings.collectLatest {
                moreInfoAdditionalSettingAdapter.submitList(it)
            }
        }

        accountAdditionalSettingsAdapter.listener = object : ProfileAdditionalSettingListener {
            override fun onProfileAdditionalSettingClicked(direction: String) {
                when(direction) {
                    AdditionalSettingConfig.ORDER_HISTORY.direction -> findNavController().navigate(
                        ProfileFragmentDirections.actionNavigationProfileToNavigationHistory()
                    )
                }
            }
        }

        moreInfoAdditionalSettingAdapter.listener = object : ProfileAdditionalSettingListener {
            override fun onProfileAdditionalSettingClicked(direction: String) {
                when(direction) {
                    AdditionalSettingConfig.LOGOUT.direction -> {
                        viewModel.logout()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoggedOut.collectLatest {
                it then {
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                it.isLoading() then { lottieDialog.show() } otherwise { lottieDialog.dismiss() }
                it.isErrorDo { Light.error(root, viewModel.message, Light.LENGTH_SHORT).show() }
            }
        }
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ProfileFragmentDirections.actionNavigationProfileToNavigationHome()
        )
    }
}