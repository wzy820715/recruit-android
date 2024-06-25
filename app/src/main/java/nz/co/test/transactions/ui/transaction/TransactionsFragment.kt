package nz.co.test.transactions.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.R
import nz.co.test.transactions.databinding.FragmentTransactionsBinding
import nz.co.test.transactions.services.Transaction
import java.math.BigDecimal
import java.time.OffsetDateTime

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var binding: FragmentTransactionsBinding

    private lateinit var listAdapter: TransactionAdapter

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