package com.keabyte.transaction_engine.transaction_api.web.model.transaction

import java.math.BigDecimal

data class CreateDepositRequest(
    val accountNumber: String,
    val amount: BigDecimal,
    val currency: String
)