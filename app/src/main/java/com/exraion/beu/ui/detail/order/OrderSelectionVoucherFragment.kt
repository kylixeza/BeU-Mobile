package com.exraion.beu.ui.detail.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exraion.beu.adapter.voucher.VoucherAdapter
import com.exraion.beu.adapter.voucher.VoucherAdapterListener
import com.exraion.beu.databinding.FragmentOrderSelectionVoucherBinding
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.util.hideWhen
import com.exraion.beu.util.isEmpty
import com.exraion.beu.util.isLoading
import com.exraion.beu.util.isNotNullThen
import com.exraion.beu.util.isSuccess
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.showWhen
import com.exraion.beu.util.then
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class OrderSelectionVoucherFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentOrderSelectionVoucherBinding? = null
    private val binding: FragmentOrderSelectionVoucherBinding? get() = _binding

    private val viewModel by activityViewModels<OrderViewModel>()
    private val voucherAdapter by inject<VoucherAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderSelectionVoucherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvVouchers?.apply {
            adapter = voucherAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserVouchers()

        lifecycleScope.launch {
            viewModel.voucherUiState.collect {
                binding?.apply {
                    pbLoading showWhen it.isLoading() hideWhen (it.isSuccess() or it.isEmpty())
                    rvVouchers showWhen it.isSuccess() hideWhen (it.isLoading() or it.isEmpty())
                    tvEmpty showWhen it.isEmpty() hideWhen (it.isLoading() or it.isSuccess())
                }
            }
        }

        lifecycleScope.launch {
            viewModel.vouchers.collect {
                voucherAdapter.submitList(it)
            }
        }

        voucherAdapter.listener = object : VoucherAdapterListener() {
            override fun onVoucherUse(voucher: VoucherDetail) {
                viewModel.applyVoucher(voucher)
            }
        }

        lifecycleScope.launch {
            viewModel.isVoucherCanBeApplied.collect {
                it isNotNullThen  {
                    it then {
                        dismiss()
                    } otherwise {
                        Toast.makeText(requireContext(), "Voucher tidak dapat digunakan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}