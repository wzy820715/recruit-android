package nz.co.test.transactions.ui.transaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import nz.co.test.transactions.R
import nz.co.test.transactions.databinding.FragmentTransactionDetailBinding
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionDetailFragment : Fragment(R.layout.fragment_transaction_detail) {

    private lateinit var binding: FragmentTransactionDetailBinding

    private val args by navArgs<TransactionDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTransactionDetailBinding.bind(view)
        initView()
    }

    private fun initView() {
        args.transaction.run {
            binding.credit.text = String.format(Locale.getDefault(), "%.2f", credit)
            binding.debit.text = String.format(Locale.getDefault(), "%.2f", debit)
            binding.summary.text = summary
            binding.transactionDate.text = transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }

}