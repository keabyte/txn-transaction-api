package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal

class AccountValuation(val totalValue: BigDecimal, val balances: List<BalanceValuation>)