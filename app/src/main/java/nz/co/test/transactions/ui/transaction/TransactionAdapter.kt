package nz.co.test.transactions.ui.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.databinding.LayoutTransactionItemBinding
import nz.co.test.transactions.services.Transaction
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.ItemViewHolder>(REPO_COMPARATOR) {

    fun interface OnItemClickListener {
        fun onItemClick(item: Transaction)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_transaction_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(getItem(adapterPosition))
            }
        }

        private val binding = LayoutTransactionItemBinding.bind(itemView)
        private val credit = binding.credit
        private val debit = binding.debit
        private val summary = binding.summary
        private val transactionDate = binding.transactionDate

        fun bind(item: Transaction) {
            credit.text = String.format(Locale.getDefault(), "%.2f", item.credit)
            debit.text = String.format(Locale.getDefault(), "%.2f", item.debit)
            summary.text = item.summary
            transactionDate.text = item.transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }

    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem.transactionDate == newItem.transactionDate
        }
    }

}