package com.exraion.beu.ui.voucher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exraion.beu.R

class VoucherFragment : Fragment() {
    
    companion object {
        fun newInstance() = VoucherFragment()
    }
    
    private lateinit var viewModel: VoucherViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voucher, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VoucherViewModel::class.java)
        // TODO: Use the ViewModel
    }
    
}