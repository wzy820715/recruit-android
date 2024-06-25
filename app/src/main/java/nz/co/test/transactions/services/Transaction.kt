package nz.co.test.transactions.services

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.time.OffsetDateTime

@Parcelize
data class Transaction(
    val id: Int,
    val transactionDate: OffsetDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
): Parcelable