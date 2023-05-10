package com.exraion.beu.ui.recognition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.exraion.beu.adapter.menu_list_vertical.MenuListVerticalAdapter
import com.exraion.beu.common.initLinearVertical
import com.exraion.beu.databinding.FragmentPredictionResultBinding
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.showWhen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PredictionResultFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPredictionResultBinding? = null
    private val binding: FragmentPredictionResultBinding? get() = _binding

    private val viewModel by activityViewModel<ImageRecognitionViewModel>()
    private val adapter by inject<MenuListVerticalAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPredictionResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchRelatedMenus()

        binding?.apply {

            rvPredictionResult.initLinearVertical(requireContext(), adapter)

            lifecycleScope.launch {
                viewModel.uiState.collect {
                    pbPredictionResult showWhen it.isLoading() hideWhen it.isSuccess()
                    rvPredictionResult showWhen it.isSuccess() hideWhen it.isLoading()
                }
            }

            lifecycleScope.launch {
                viewModel.relatedMenus.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}