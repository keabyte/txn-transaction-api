package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.service.CreateInvestmentParameters
import com.keabyte.transaction_engine.transaction_api.type.TransactionType


data class CreateTransactionParameters(
    val transactionType: TransactionType,
    val investments: List<CreateInvestmentParameters>
)