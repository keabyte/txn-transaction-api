package com.keabyte.transaction_engine.transaction_api.service.dto

import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import java.math.BigDecimal

data class CreateInvestmentParameters(
    val accountNumber: String,
    val amount: BigDecimal,
    val currency: String,
    val balanceEffectType: BalanceEffectType,
    val assetCode: String
)