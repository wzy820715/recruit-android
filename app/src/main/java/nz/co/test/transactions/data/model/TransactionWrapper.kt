package nz.co.test.transactions.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import nz.co.test.transactions.services.Transaction
import java.math.BigDecimal
import java.time.OffsetDateTime

@Parcelize
data class TransactionWrapper(
    val id: Int,
    val transactionDate: OffsetDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal,
    val GST: BigDecimal
) : Parcelable {
    companion object {
        fun convert(item: Transaction): TransactionWrapper {
            return TransactionWrapper(
                id = item.id,
                transactionDate = item.transactionDate,
                summary = item.summary,
                debit = item.debit,
                credit = item.credit,
                GST = (item.credit + item.debit).multiply(BigDecimal("0.15"))
            )
        }
    }
}