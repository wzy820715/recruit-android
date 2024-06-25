package nz.co.test.transactions.data.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class TransactionWrapper(
    val id: Int,
    val transactionDate: OffsetDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal,
    val GST: BigDecimal
)