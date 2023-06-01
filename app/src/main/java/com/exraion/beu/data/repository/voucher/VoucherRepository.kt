package com.exraion.beu.data.repository.voucher

import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherAvailable
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.model.VoucherList
import com.exraion.beu.model.VoucherSecret
import kotlinx.coroutines.flow.Flow

interface VoucherRepository {
    fun fetchAvailableVouchers(): Flow<Resource<VoucherAvailable>>
    fun redeemVoucher(voucherId: String, costXp: Int): Flow<Resource<Unit>>
    fun fetchUserVouchers(): Flow<Resource<List<VoucherList>>>
    fun fetchVoucherDetail(voucherId: String): Flow<Resource<VoucherDetail>>
    fun useVoucher(voucherId: String): Flow<Resource<String>>
    fun redeemVoucherBySecretKey(secretKey: String): Flow<Resource<VoucherSecret>>
}