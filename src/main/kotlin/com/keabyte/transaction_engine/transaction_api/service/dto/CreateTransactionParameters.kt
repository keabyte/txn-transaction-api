package com.keabyte.transaction_engine.transaction_api.service.dto

import com.keabyte.transaction_engine.transaction_api.type.TransactionType


data class CreateTransactionParameters(
    val transactionType: TransactionType,
    val investments: List<CreateInvestmentParameters>
)