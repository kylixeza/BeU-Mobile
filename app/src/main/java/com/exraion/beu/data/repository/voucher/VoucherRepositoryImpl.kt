package com.exraion.beu.data.repository.voucher

import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherAvailableResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherDetailResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherListResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherAvailable
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.model.VoucherList
import com.exraion.beu.util.toVoucherAvailable
import com.exraion.beu.util.toVoucherDetail
import com.exraion.beu.util.toVoucherList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VoucherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): VoucherRepository {
    override fun fetchAvailableVouchers(): Flow<Resource<VoucherAvailable>> =
        object : NetworkOnlyResource<VoucherAvailable, VoucherAvailableResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<VoucherAvailableResponse?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchAvailableVouchers(token)
            }

            override fun mapTransform(data: VoucherAvailableResponse?): VoucherAvailable {
                return data!!.toVoucherAvailable()
            }

        }.asFlow()

    override fun redeemVoucher(voucherId: String): Flow<Resource<String>> {
        return object : NetworkOnlyResource<String, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.redeemVoucher(token, voucherId)
            }

            override fun mapTransform(data: String?): String {
                return data.orEmpty()
            }

        }.asFlow()
    }

    override fun fetchUserVouchers(): Flow<Resource<List<VoucherList>>> =
        object : NetworkOnlyResource<List<VoucherList>, List<VoucherListResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<VoucherListResponse>?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchUserVouchers(token)
            }

            override fun mapTransform(data: List<VoucherListResponse>?): List<VoucherList> {
                return data!!.map { it.toVoucherList() }
            }

        }.asFlow()

    override fun fetchVoucherDetail(voucherId: String): Flow<Resource<VoucherDetail>> =
        object : NetworkOnlyResource<VoucherDetail, VoucherDetailResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<VoucherDetailResponse?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchVoucherDetail(token, voucherId)
            }

            override fun mapTransform(data: VoucherDetailResponse?): VoucherDetail {
                return data!!.toVoucherDetail()
            }

        }.asFlow()

    override fun useVoucher(voucherId: String): Flow<Resource<String>> =
        object : NetworkOnlyResource<String, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.useVoucher(token, voucherId)
            }

            override fun mapTransform(data: String?): String {
                return data.orEmpty()
            }

        }.asFlow()

    override fun redeemVoucherBySecretKey(secretKey: String): Flow<Resource<String>> =
        object : NetworkOnlyResource<String, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.redeemVoucherBySecretKey(token, secretKey)
            }

            override fun mapTransform(data: String?): String {
                return data.orEmpty()
            }

        }.asFlow()
}