package com.exraion.beu.data.repository.order

import com.exraion.beu.data.source.remote.api.model.history.HistoryUpdateStarsGiven
import com.exraion.beu.data.source.remote.api.model.order.OrderBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.History
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun postOrder(body: OrderBody): Flow<Resource<Unit>>
    fun fetchUserOrders(): Flow<Resource<List<History>>>
    fun cancelOrder(orderId: String): Flow<Resource<Unit>>
    fun rateOrder(orderId: String, body: HistoryUpdateStarsGiven): Flow<Resource<Unit>>
}