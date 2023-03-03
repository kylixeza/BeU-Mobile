package com.exraion.beu.ui.profile

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.adapter.profile_additional_settings.ProfileAdditionalSettingAdapter
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.common.initLinearHorizontal
import com.exraion.beu.common.initLinearVertical
import com.exraion.beu.databinding.FragmentProfileBinding
import com.exraion.beu.util.ScreenOrientation
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    
    private val viewModel by viewModel<ProfileViewModel>()
    private val accountAdditionalSettingsAdapter by inject<ProfileAdditionalSettingAdapter>()
    private val moreInfoAdditionalSettingAdapter by inject<ProfileAdditionalSettingAdapter>()
    
    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentProfileBinding.binder() {
        
        rvAccountSettings.initLinearVertical(requireContext(), accountAdditionalSettingsAdapter)
        rvMoreInfoSettings.initLinearVertical(requireContext(), moreInfoAdditionalSettingAdapter)
        
        lifecycleScope.launchWhenStarted {
            viewModel.user.collect {
                Glide.with(requireContext())
                    .load(it?.avatar)
                    .placeholder(R.drawable.ilu_default_profile_picture)
                    .into(ivProfile)
    
                tvName.text = it?.name
                tvEmail.text = it?.email
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.accountAdditionalSettings.collectLatest {
                accountAdditionalSettingsAdapter.submitList(it)
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.moreInfoAdditionalSettings.collectLatest {
                moreInfoAdditionalSettingAdapter.submitList(it)
            }
        }
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ProfileFragmentDirections.actionNavigationProfileToNavigationHome()
        )
    }
}