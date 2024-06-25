package nz.co.test.transactions.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.data.repository.RequestState
import nz.co.test.transactions.data.repository.TransactionRepository
import javax.inject.Inject

@HiltViewModel
class TransactionVM @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    private val _transactionsStatFlow: MutableStateFlow<RequestState> = MutableStateFlow(RequestState.Loading)
    val transactionsStatFlow = _transactionsStatFlow.asStateFlow()

    init {
        getTransactions()
    }

    private fun getTransactions() = viewModelScope.launch {
        repository.getTransactions().collect {
            _transactionsStatFlow.value = it
        }
    }
}