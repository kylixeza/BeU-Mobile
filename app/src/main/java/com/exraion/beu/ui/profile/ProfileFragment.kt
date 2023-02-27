package com.exraion.beu.ui.profile

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.exraion.beu.R
import com.exraion.beu.base.BaseFragment
import com.exraion.beu.databinding.FragmentProfileBinding
import com.exraion.beu.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    
    private val viewModel by viewModel<ProfileViewModel>()
    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater, container, false)
    }
    
    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
    
    override fun FragmentProfileBinding.binder() {
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
    }
    
    override fun onBackPressedBehaviour() {
        findNavController().navigate(
            ProfileFragmentDirections.actionNavigationProfileToNavigationHome()
        )
    }
}