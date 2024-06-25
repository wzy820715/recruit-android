package nz.co.test.transactions.ui.transaction

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nz.co.test.transactions.R
import nz.co.test.transactions.data.remote.TransactionsRemoteDataSource
import nz.co.test.transactions.databinding.FragmentTransactionsBinding
import nz.co.test.transactions.services.Transaction
import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var binding: FragmentTransactionsBinding

    private lateinit var listAdapter: TransactionAdapter

    @Inject
    lateinit var transactionsRemoteDataSource: TransactionsRemoteDataSource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionsBinding.bind(view)
        initView()
        mockDataTest()
    }

    private fun initView() {
        listAdapter = TransactionAdapter().apply {
            setOnItemClickListener {
                val action = TransactionsFragmentDirections.actionToDetail(it)
                findNavController().navigate(action)
            }
        }
        binding.list.apply {
            addItemDecoration(
                DividerItemDecoration(this@TransactionsFragment.context, DividerItemDecoration.VERTICAL)
            )
            layoutManager = LinearLayoutManager(this@TransactionsFragment.context)
            adapter = listAdapter
        }
    }

    private fun mockDataTest() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                transactionsRemoteDataSource.requestTransactions().let {
                    Log.i("123123", "$it")
                }
            }
        }
        val list = mutableListOf<Transaction>()
        repeat(20) {
            list.add(
                Transaction(
                    id = it,
                    summary = "item $it",
                    debit = BigDecimal(it),
                    credit = BigDecimal(it),
                    transactionDate = OffsetDateTime.now()
                )
            )
        }
        listAdapter.submitList(list)
    }


}