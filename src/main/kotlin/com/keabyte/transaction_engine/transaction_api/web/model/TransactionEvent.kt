package com.keabyte.transaction_engine.transaction_api.web.model

import com.keabyte.transaction_engine.transaction_api.type.TransactionType
import java.time.OffsetDateTime

data class TransactionEvent(
    val transactionReference: String,
    val createdDate: OffsetDateTime,
    val type: TransactionType,
    val accountTransactions: List<AccountTransaction>
)