package nz.co.test.transactions.data.remote

import nz.co.test.transactions.services.TransactionsService
import javax.inject.Inject

class TransactionsRemoteDataSource @Inject constructor(
    private val apiService: TransactionsService
) {
    suspend fun requestTransactions() = apiService.retrieveTransactions()
}