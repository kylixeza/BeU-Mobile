package com.exraion.beu.data.repository.order

import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.history.HistoryResponse
import com.exraion.beu.data.source.remote.api.model.history.HistoryUpdateStarsGiven
import com.exraion.beu.data.source.remote.api.model.order.OrderBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.History
import com.exraion.beu.util.toHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class OrderRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): OrderRepository {
    override fun postOrder(body: OrderBody): Flow<Resource<Unit>> =
        object : NetworkOnlyResource<Unit, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.postOrder(token, body)
            }

            override fun mapTransform(data: String?) {
                return
            }

        }.asFlow()

    override fun fetchUserOrders(): Flow<Resource<List<History>>> =
        object : NetworkOnlyResource<List<History>, List<HistoryResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<HistoryResponse>?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchUserOrders(token)
            }

            override fun mapTransform(data: List<HistoryResponse>?): List<History> {
                return data?.map { it.toHistory() } ?: emptyList()
            }

        }.asFlow()

    override fun cancelOrder(orderId: String): Flow<Resource<Unit>> =
        object : NetworkOnlyResource<Unit, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.cancelOrder(token, orderId)
            }

            override fun mapTransform(data: String?) {
                return
            }

        }.asFlow()

    override fun rateOrder(orderId: String, body: HistoryUpdateStarsGiven): Flow<Resource<Unit>> =
        object : NetworkOnlyResource<Unit, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.rateOrder(token, orderId, body)
            }

            override fun mapTransform(data: String?) {
                return
            }

        }.asFlow()

}