package nz.co.test.transactions.data.repository

import kotlinx.coroutines.flow.flow
import nz.co.test.transactions.data.model.TransactionWrapper
import nz.co.test.transactions.data.remote.TransactionsRemoteDataSource
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val remoteDataSource: TransactionsRemoteDataSource
) {

    fun getTransactions() = flow {
        emit(RequestState.Loading)

        kotlin.runCatching {
            remoteDataSource.requestTransactions()
        }.onSuccess { list ->
            list.map {item ->
                TransactionWrapper.convert(item)
            }.sortedByDescending {
                it.transactionDate
            }.let { result ->
                emit(RequestState.Success(result))
            }
        }.onFailure {
            emit(RequestState.Error(IllegalArgumentException(it)))
        }
    }

}

sealed class RequestState {
    object Loading : RequestState()
    data class Success(val data: List<TransactionWrapper>) : RequestState()
    data class Error(val exception: Exception) : RequestState()
}