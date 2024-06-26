package nz.co.test.transactions.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import nz.co.test.transactions.R
import nz.co.test.transactions.data.repository.RequestState
import nz.co.test.transactions.databinding.FragmentTransactionsBinding

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var binding: FragmentTransactionsBinding

    private val viewModule: TransactionVM by activityViewModels()

    private lateinit var listAdapter: TransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionsBinding.bind(view)
        initView()
        observeData()
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

    private fun observeData() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModule.transactionsStatFlow.collect {
                when (it) {
                    is RequestState.Loading -> binding.progressBar.isVisible = true

                    is RequestState.Success -> {
                        binding.progressBar.isVisible = false
                        listAdapter.submitList(it.data.toList())
                    }

                    is RequestState.Error -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            this@TransactionsFragment.context,
                            it.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}