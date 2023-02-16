package com.exraion.beu.base

import com.exraion.beu.data.source.local.LocalAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.SQLException

abstract class BaseDatabaseAnswer<RequestType> {

    private val flowValue = flow {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty())
                    emit(LocalAnswer.Empty())
                else
                    emit(LocalAnswer.Success(value))
            } else {
                emit(LocalAnswer.Success(value))
            }
        } catch (e: SQLException) {
            emit(LocalAnswer.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun singleValue(): LocalAnswer<RequestType> {
        try {
            val value = callDatabase()
            if(value is List<*>) {
                if(value.isEmpty()) {
                    return LocalAnswer.Empty()
                }
                return LocalAnswer.Success(value)
            }
            return LocalAnswer.Success(value)
        } catch (e: SQLException) {
            return LocalAnswer.Error(e.toString())
        }
    }

    abstract suspend fun callDatabase(): RequestType

    fun doObservable() = flowValue
    suspend fun doSingleEvent() = singleValue()

}