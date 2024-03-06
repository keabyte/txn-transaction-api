package com.keabyte.transaction_engine.transaction_api.web.model

import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import java.math.BigDecimal

class InvestmentTransaction(val amount: BigDecimal, val currency: String, val balanceEffectType: BalanceEffectType)