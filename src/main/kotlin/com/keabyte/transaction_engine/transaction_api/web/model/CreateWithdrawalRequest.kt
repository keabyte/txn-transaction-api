package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal

data class CreateWithdrawalRequest(
    val accountNumber: String,
    val amount: BigDecimal,
    val currency: String
)