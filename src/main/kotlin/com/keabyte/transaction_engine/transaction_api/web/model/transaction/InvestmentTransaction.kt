package com.keabyte.transaction_engine.transaction_api.web.model.transaction

import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import java.math.BigDecimal

class InvestmentTransaction(
    val amount: BigDecimal,
    val units: BigDecimal,
    val currency: String,
    val balanceEffectType: BalanceEffectType,
    val assetCode: String
)